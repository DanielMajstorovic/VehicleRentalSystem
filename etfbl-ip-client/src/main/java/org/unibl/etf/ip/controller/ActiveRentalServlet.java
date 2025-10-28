package org.unibl.etf.ip.controller;

import java.io.*;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.unibl.etf.ip.dao.*;
import org.unibl.etf.ip.model.*;

@WebServlet("/active-rental")
@MultipartConfig
public class ActiveRentalServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		User client = (User) req.getSession().getAttribute("client");
		try {
			Rental active = new RentalDao().findActiveByClient(client.getId());
			if (active == null) {
				resp.sendRedirect("home");
				return;
			}
			req.setAttribute("activeRental", active);
			req.getRequestDispatcher("/WEB-INF/views/active_rental.jsp").forward(req, resp);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int rentalId = Integer.parseInt(req.getParameter("rentalId"));
		String vehicleUid = req.getParameter("vehicleUid");
		int clientId = Integer.parseInt(req.getParameter("clientId"));

		String sx = req.getParameter("endX");
		String sy = req.getParameter("endY");
		if (sx == null || sy == null || sx.isBlank() || sy.isBlank()) {
			resp.sendRedirect("active-rental");
			return;
		}
		double endX = Double.parseDouble(sx);
		double endY = Double.parseDouble(sy);
		double priceSec = Double.parseDouble(req.getParameter("pricePerSec"));
		Instant start = Instant.parse(req.getParameter("startIso"));
		Instant end = Instant.now();

		int duration = (int) Duration.between(start, end).getSeconds();
		double total = duration * priceSec;

		try {
			RentalDao rDao = new RentalDao();
			rDao.finishRental(rentalId, Timestamp.from(end), endX, endY, duration, total);

			new VehicleDao().setPositionAndAvailable(vehicleUid, endX, endY);
			new ClientDao().adjustBalance(clientId, -total);
			
			String nameString = new ClientDao().getFullNameByClientId(clientId);

			byte[] pdfBytes = generatePdf(rentalId, start, end, duration, total, vehicleUid, nameString);
			resp.setContentType("application/pdf");
			resp.setContentLength(pdfBytes.length);
			resp.getOutputStream().write(pdfBytes);

		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	public byte[] generatePdf(int rentalId, Instant start, Instant end, int durationSeconds, double total, String vehicleUid, String nameString)
			throws IOException {

		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.of("Europe/Sarajevo"));

		String startFmt = fmt.format(start);
		String endFmt = fmt.format(end);
		String issuedAt = fmt.format(Instant.now());
		String durationFmt = formatDuration(durationSeconds);

		try (PDDocument doc = new PDDocument(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

			PDPage page = new PDPage(PDRectangle.LETTER);
			doc.addPage(page);


			String imgPath = getServletContext().getRealPath("/assets/images/etf.png");
			PDImageXObject bg = PDImageXObject.createFromFile(imgPath, doc);
			try (PDPageContentStream bgStream = new PDPageContentStream(doc, page,
					PDPageContentStream.AppendMode.PREPEND, true, true)) {
				bgStream.drawImage(bg, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());
			}


			try (PDPageContentStream cs = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND,
					true, true)) {


				cs.beginText();
				cs.setFont(PDType1Font.HELVETICA_BOLD, 18);
				cs.newLineAtOffset(40, 750);
				cs.showText("Vehicle Rental Receipt");
				cs.endText();


				cs.beginText();
				cs.setFont(PDType1Font.HELVETICA, 12);
				cs.newLineAtOffset(40, 730);
				cs.showText("Dear " + nameString + ", ");
				cs.endText();


				cs.beginText();
				cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
				cs.newLineAtOffset(40, 710);
				cs.showText("Company: ETFBL_IP");
				cs.endText();


				cs.beginText();
				cs.setFont(PDType1Font.HELVETICA, 12);
				cs.newLineAtOffset(40, 690);
				cs.showText("This is your invoice for the rental services we provided.");
				cs.endText();


				int y = 650;
				List<String[]> rows = Arrays.asList(new String[] { "Rental ID:", String.valueOf(rentalId) },
						new String[] { "Invoice for vehicle:", String.valueOf(vehicleUid) },
						new String[] { "Start:", startFmt }, new String[] { "End:", endFmt },
						new String[] { "Duration:", durationFmt },
						new String[] { "Total:", String.format("$%.2f", total) },
						new String[] { "                                        ",  "<- This will be charged from your balance. ->" },
						new String[] { "Generated at:", issuedAt });

				for (String[] row : rows) {
					
					cs.beginText();
					cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
					cs.newLineAtOffset(40, y);
					cs.showText(row[0]);
					cs.endText();

					
					cs.beginText();
					cs.setFont(PDType1Font.HELVETICA, 12);
					cs.newLineAtOffset(150, y);
					cs.showText(row[1]);
					cs.endText();

					y -= 20;
				}


				cs.beginText();
				cs.setFont(PDType1Font.HELVETICA_OBLIQUE, 12);
				cs.newLineAtOffset(40, 80);
				cs.showText("Thank you for your trust!");
				cs.endText();
			}

			doc.save(baos);
			return baos.toByteArray();
		}
	}


	private String formatDuration(int totalSeconds) {
		int secs = totalSeconds;
		int days = secs / 86_400;
		secs %= 86_400;
		int hours = secs / 3_600;
		secs %= 3_600;
		int mins = secs / 60;
		secs %= 60;

		StringBuilder b = new StringBuilder();
		if (days > 0)
			b.append(days).append(" day").append(days > 1 ? "s " : " ");
		if (hours > 0)
			b.append(hours).append(" hr").append(hours > 1 ? "s " : " ");
		if (mins > 0)
			b.append(mins).append(" min ");
		if (secs > 0 || b.length() == 0)
			b.append(secs).append(" sec");

		return b.toString().trim();
	}

}
