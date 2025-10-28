<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="includes/head.jsp"%>
<body>
	<div class="login-container">
		<div class="background-overlay"></div>
		<div class="login-card">
			<div class="card-header">
				<div class="logo-container">
					<i class="bi bi-car-front-fill"></i>
				</div>
				<h2>ETFBL_IP</h2>
				<p class="text-muted">Vehicle Rental System</p>
			</div>
			<div class="card-body">
				<h3 class="mb-4">Manager Login</h3>
				<form id="loginForm">
					<div class="mb-4">
						<label class="form-label">Username</label>
						<div class="input-group">
							<span class="input-group-text"><i class="bi bi-person"></i></span>
							<input type="text" id="username" class="form-control"
								placeholder="Enter your username" required>
						</div>
						<div id="userError" class="text-danger mt-1"
							style="display: none;">
							<small>Username is required</small>
						</div>
					</div>
					<div class="mb-4">
						<label class="form-label">Password</label>
						<div class="input-group">
							<span class="input-group-text"><i class="bi bi-lock"></i></span>
							<input type="password" id="password" class="form-control"
								placeholder="Enter your password" required>
							<button class="input-group-text password-toggle" type="button"
								onclick="togglePassword()" tabindex="-1">
								<i class="bi bi-eye"></i>
							</button>
						</div>
						<div id="passError" class="text-danger mt-1"
							style="display: none;">
							<small>Password is required</small>
						</div>
					</div>
					<div class="d-grid gap-2">
						<button type="submit" class="btn btn-primary btn-lg">
							<i class="bi bi-box-arrow-in-right me-2"></i>Login
						</button>
					</div>
				</form>
				<div id="loginError" class="text-danger mt-2"></div>
			</div>
		</div>
	</div>

	<script>
		function togglePassword() {
			const pwd = document.getElementById('password');
			const icon = document.querySelector('.password-toggle i');
			if (pwd.type === 'password') {
				pwd.type = 'text';
				icon.classList.replace('bi-eye', 'bi-eye-slash');
			} else {
				pwd.type = 'password';
				icon.classList.replace('bi-eye-slash', 'bi-eye');
			}
		}

		$('#loginForm')
				.submit(
						function(e) {
							e.preventDefault();
							$('#loginError').text('');
							const user = $('#username').val().trim();
							const pass = $('#password').val().trim();
							if (!user) {
								$('#userError').show();
								return;
							} else
								$('#userError').hide();
							if (!pass) {
								$('#passError').show();
								return;
							} else
								$('#passError').hide();

							$
									.ajax({
										url : 'http://localhost:8080/login',
										method : 'POST',
										contentType : 'application/json',
										data : JSON.stringify({
											username : user,
											password : pass
										}),
										success : function(res) {
											if (res.role !== 'MANAGER') {
												$('#loginError')
														.text(
																'Only for managers!');
											} else {
												sessionStorage.setItem('role',
														'MANAGER');
												window.location = '${pageContext.request.contextPath}/feed';
											}
										},
										error : function(xhr) {
											$('#loginError').text(
													xhr.responseText);
										}
									});
						});
	</script>

	<%@ include file="footer.jsp"%>
	