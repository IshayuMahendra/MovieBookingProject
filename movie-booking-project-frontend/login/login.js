const loginForm = document.getElementById('loginForm');
const errorBox = document.getElementById('errorBox');
const forgotPasswordBtn = document.getElementById('forgotPasswordBtn');
const signupRedirect = document.getElementById('signupRedirect');

loginForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    errorBox.classList.add('hidden');

    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value;
    const rememberMe = document.getElementById('rememberMe').checked;

    if (!email || !password) {
        errorBox.textContent = "Please fill in all fields.";
        errorBox.classList.remove('hidden');
        return;
    }

    try {
        const res = await fetch('http://localhost:8080/user/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, password })
        });

        const text = await res.text(); // parse plain text

    if (res.ok) {
    // Fetch full user object by email
    const userRes = await fetch(`http://localhost:8080/user/${encodeURIComponent(email)}`);
    
    if (!userRes.ok) throw new Error("Failed to fetch user data");

    const userData = await userRes.json();

    // Save userID and email in sessionStorage
    sessionStorage.setItem('loggedInUser', JSON.stringify({
        id: userData.ObjectId,   // must match Booking.userID
        email: userData.email
    }));

    // Optionally remember email
    if (rememberMe) {
        localStorage.setItem('rememberedEmail', email);
    }

    // Redirect to home
    window.location.href = '../home/index.html';
} else {
    errorBox.textContent = text;
    errorBox.classList.remove('hidden');
}

    } catch (err) {
        errorBox.textContent = "Login failed. Try again later.";
        errorBox.classList.remove('hidden');
    }
});

// Forgot password button
forgotPasswordBtn.addEventListener('click', () => {
    const email = document.getElementById('email').value.trim();
    // if (!email) {
    //     errorBox.textContent = "Enter your email to reset password.";
    //     errorBox.classList.remove('hidden');
    //     return;
    // }
    window.location.href = '../forgot-password/forgot.html?email=' + encodeURIComponent(email);
});

// Redirect to signup
signupRedirect.addEventListener('click', () => {
    window.location.href = '../registration/signup.html';
});

const adminLoginBtn = document.getElementById('adminLoginBtn');

adminLoginBtn.addEventListener('click', async () => {
    const userID = prompt("Enter admin ID:");
    const password = prompt("Enter password:");

    if (!userID || !password) return alert("Admin ID and password required");

    try {
        const res = await fetch('http://localhost:8080/admin/login', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({ userID, password })
        });

        const text = await res.text();

        if (res.ok) {
            // Save admin info in session
            sessionStorage.setItem('loggedInAdmin', JSON.stringify({ userID }));
            // Redirect to home
            window.location.href = '../admin/admin.html';
        } else {
            alert(text);
        }
    } catch (err) {
        alert("Admin login failed. Try again later.");
    }
});


