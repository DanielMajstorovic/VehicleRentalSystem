const now = new Date();
let currentFilter = 'all';

function formatDate(str) {
	if (!str) return '';
	const d = new Date(str);
	return d.toLocaleString('en-GB', {
		day: '2-digit',
		month: 'short',
		year: 'numeric',
		hour: '2-digit',
		minute: '2-digit',
		hour12: false 
	});
}

function updateCounter() {
	$('#countLabel').text(`Showing: ${$('#cards .col-md-4:visible').length}`);
}

function loadFeed() {
	$.get('http://localhost:8080/api/rss/feed', function(xml) {
		$('#cards').empty();

		$(xml).find('item').each(function() {
			const $it = $(this);
			const title = $it.find('title').text();
			const desc = $it.find('description').text();
			const cat = $it.find('category').text();          
			const start = $it.find('startsAt').text();
			const end = $it.find('endsAt').text();
			const expired = cat === 'promotion' && end && new Date(end) < now;

			const $col = $('<div class="col-md-4"></div>')
				.attr({ 'data-category': cat, 'data-endsat': end });

			const $card = $('<div class="card shadow-sm"></div>')
				.addClass(cat === 'promotion' ? 'card-promotion' : 'card-post')
				.toggleClass('expired', expired);

			const icon = cat === 'promotion'
				? '<i class="bi bi-megaphone-fill me-1"></i>'
				: '<i class="bi bi-newspaper me-1"></i>';

			const $body = $(`
        <div class="card-body">
          <h5 class="card-title">${icon}${title}</h5>
          <p class="card-text">${desc}</p>
        </div>`);

			if (cat === 'promotion') {
				$body.append(`
          <p class="mb-0">
            <i class="bi bi-calendar-event me-1"></i>
            <small>${formatDate(start)} – ${formatDate(end)}</small>
          </p>`);
				if (expired) $body.append('<span class="badge bg-danger-subtle text-danger mt-2">Expired</span>');
			}

			$card.append($body);
			$col.append($card);
			$('#cards').append($col);
		});

		applyFilters();
	});
}

function applyFilters() {
	const q = $('#search').val().toLowerCase();

	$('#cards .col-md-4').each(function() {
		const $col = $(this);
		const cat = $col.data('category');
		const end = $col.data('endsat');
		const title = $col.find('.card-title').text().toLowerCase();

		let ok = title.includes(q);

		switch (currentFilter) {
			case 'post': ok = ok && cat === 'post'; break;
			case 'promotion': ok = ok && cat === 'promotion'; break;
			case 'active-promo': ok = ok && cat === 'promotion' && (!end || new Date(end) >= now); break;
			default: /* all */;
		}
		$col.toggle(ok);
	});

	updateCounter();
}

$('#search').on('input', applyFilters);

$('.filter-btn').on('click', function() {
	$('.filter-btn').removeClass('active');
	$(this).addClass('active');
	currentFilter = $(this).data('filter');
	applyFilters();
});


const promoModal = bootstrap.Modal.getOrCreateInstance(
    document.getElementById('promoModal')
);
$('#promoForm').submit(function(e) {
	e.preventDefault();
	$.ajax({
		url: 'http://localhost:8080/api/promotions',
		method: 'POST',
		contentType: 'application/json',
		data: JSON.stringify({
			title: $('#promoTitle').val(),
			description: $('#promoDesc').val(),
			startsAt: new Date($('#promoStart').val()).toISOString(),
			endsAt: new Date($('#promoEnd').val()).toISOString()
		}),
		success: () => { promoModal.hide(); loadFeed(); }
	});
});

const postModal  = bootstrap.Modal.getOrCreateInstance(
    document.getElementById('postModal')
);
$('#postForm').submit(function(e) {
	e.preventDefault();
	$.ajax({
		url: 'http://localhost:8080/api/posts',
		method: 'POST',
		contentType: 'application/json',
		data: JSON.stringify({
			title: $('#postTitle').val(),
			content: $('#postContent').val()
		}),
		success: () => { postModal.hide(); loadFeed(); }
	});
});

$(document).ready(function() {
	if (sessionStorage.getItem('role') !== 'MANAGER') {
		window.location = '${pageContext.request.contextPath}/login';
	} else {
		loadFeed();
	}
});
