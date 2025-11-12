const forgotForm = document.getElementById('forgotForm');
const errorBox = document.getElementById('errorBox');
const successBox = document.getElementById('successBox');

forgotForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    errorBox.classList.add('hidden');
    successBox.classList.add('hidden');

    const email = document.getElementById('email').value.trim();
    if (!email) {
        errorBox.textContent = "Please enter your email.";
        errorBox.classList.remove('hidden');
        return;
    }

    try {
        const res = await fetch('http://localhost:8080/user/forgot', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email })
        });

        const text = await res.text();
        successBox.textContent = text;
        successBox.classList.remove('hidden');
    } catch (err) {
        errorBox.textContent = "Failed to send reset link. Try again later.";
        errorBox.classList.remove('hidden');
        console.error(err);
    }
});
