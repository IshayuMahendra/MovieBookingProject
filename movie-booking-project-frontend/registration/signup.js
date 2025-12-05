const signupForm = document.getElementById('signupForm');
const errorBox = document.getElementById('errorBox');
const loginRedirect = document.getElementById('loginRedirect');
const addCardBtn = document.getElementById('addCardBtn');
const cardsContainer = document.getElementById('cardsContainer');

/* =================== ADD CARD HANDLER =================== */
if (addCardBtn) {
    addCardBtn.addEventListener('click', () => {
        const currentCards = cardsContainer.querySelectorAll('.card-entry').length;
        if (currentCards >= 3) return alert('Max 3 cards allowed.');

        const div = document.createElement('div');
        div.className = 'card-entry';
        div.innerHTML = `
            <input type="text" class="cardNumber" placeholder="Card Number*" required>
            <input type="text" class="expirationDate" placeholder="MM/YY*" required>
            <input type="text" class="billingAddress" placeholder="Billing Address*" required>
            <button type="button" class="deleteCardBtn">Delete</button>
        `;
        div.querySelector('.deleteCardBtn').addEventListener('click', () => div.remove());
        cardsContainer.appendChild(div);
    });
}

/* =================== SIGN UP FORM =================== */

signupForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    errorBox.classList.add('hidden');

    const userID = document.getElementById('userID').value.trim();
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const firstName = document.getElementById('firstName').value.trim();
    const lastName = document.getElementById('lastName').value.trim();
    const street = document.getElementById('street').value.trim();
    const city = document.getElementById('city').value.trim();
    const state = document.getElementById('state').value.trim();
    const zipCode = document.getElementById('zipCode').value.trim();
    const promotions = document.getElementById('promotions').checked;

    // Frontend validation
    if (password !== confirmPassword) {
        errorBox.textContent = "Passwords do not match.";
        errorBox.classList.remove('hidden');
        return;
    }

    // Collect payment cards
    const cards = [];
    cardsContainer.querySelectorAll('.card-entry').forEach(div => {
        const cardNumber = div.querySelector('.cardNumber')?.value.trim();
        const expirationDate = div.querySelector('.expirationDate')?.value.trim();
        const billingAddress = div.querySelector('.billingAddress')?.value.trim();
        if (!cardNumber || !expirationDate || !billingAddress)  return;
        if (cardNumber.length != 16) return;
        if (expirationDate[2] != "/") return;
        const splicedDate = expirationDate.slice(0, 2) + expirationDate.slice(3);
        if (splicedDate.length != 4 || !parseInt(splicedDate, 10)) return;
        cards.push({ cardNumber, expirationDate, billingAddress });
    });

    const payload = {
        userID, password, email, firstName, lastName, street, city, state, zipCode, promotions, cards
    };
 try {
        // 1️⃣ Create user first
        const res = await fetch('http://localhost:8080/user/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userID, password, email, firstName, lastName, street, city, state, zipCode, promotions })
        });

        if (!res.ok) {
            const text = await res.text();
            throw new Error(text);
        }

        // 2️⃣ Save cards separately
        if (cards.length > 0) {
            const resCards = await fetch(`http://localhost:8080/user/edit-cards?email=${encodeURIComponent(email)}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(cards)
            });
            if (!resCards.ok) {
                const text = await resCards.text();
                throw new Error("Failed to save cards: " + text);
            }
        }

        // Redirect after success
        window.location.href = './thankyou.html';
    } catch (err) {
        errorBox.textContent = err.message;
        errorBox.classList.remove('hidden');
    }
});

// redirect to login page
loginRedirect.addEventListener('click', () => {
    window.location.href = '../login/login.html';
});
