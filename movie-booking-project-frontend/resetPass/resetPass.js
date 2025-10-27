const resetForm = document.getElementById('resetForm');
const errorBox = document.getElementById('errorBox');

// Get token from URL query parameters
const params = new URLSearchParams(window.location.search);
const token = params.get('token');

if (!token) {
    errorBox.textContent = "Invalid or missing token.";
    errorBox.classList.remove('hidden');
    resetForm.style.display = 'none';
}

resetForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    errorBox.classList.add('hidden');

    const newPassword = document.getElementById('newPassword').value.trim();
    const confirmPassword = document.getElementById('confirmPassword').value.trim();

    if (!newPassword || !confirmPassword) {
        errorBox.textContent = "Please fill in both password fields.";
        errorBox.classList.remove('hidden');
        return;
    }

    if (newPassword !== confirmPassword) {
        errorBox.textContent = "Passwords do not match.";
        errorBox.classList.remove('hidden');
        return;
    }

    try {
        const res = await fetch('http://localhost:8080/user/reset', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ token, newPassword, confirm: confirmPassword })
        });

        const text = await res.text();

        if (res.ok) {
            alert("Password reset successfully! You can now log in.");
            window.location.href = '../login/login.html';
        } else {
            errorBox.textContent = text;
            errorBox.classList.remove('hidden');
        }
    } catch (err) {
        errorBox.textContent = "Failed to reset password. Try again later.";
        errorBox.classList.remove('hidden');
        console.error(err);
    }
});
