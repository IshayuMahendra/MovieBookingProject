const user = JSON.parse(sessionStorage.getItem('loggedInUser'));
const form = document.getElementById('editProfileForm');
const statusMessage = document.getElementById('statusMessage');
const cardsContainer = document.getElementById('cardsContainer');
const addCardBtn = document.getElementById('addCardBtn');

let cards = []; // array to hold card objects: { cardNumber, expirationDate, billingAddress }

// Fetch user info and populate form
async function populateForm() {
    try {
        const res = await fetch(`http://localhost:8080/user/${encodeURIComponent(user.email)}`);
        const data = await res.json();

        document.getElementById('email').value = data.email;
        document.getElementById('firstName').value = data.firstName || '';
        document.getElementById('lastName').value = data.lastName || '';
        document.getElementById('street').value = data.street || '';
        document.getElementById('city').value = data.city || '';
        document.getElementById('state').value = data.state || '';
        document.getElementById('zip').value = data.zipCode || '';
        document.getElementById('promotions').checked = data.promotions;

        // Fetch cards from backend
        const resCards = await fetch(`http://localhost:8080/user/cards?email=${encodeURIComponent(user.email)}`);
        const cardData = await resCards.json();
        cards = cardData.map(c => ({
            cardNumber: c.cardNumber, // encrypted or decrypted depending on backend
            expirationDate: c.expirationDate,
            billingAddress: c.billingAddress
        }));
        renderCards();
    } catch (err) {
        console.error('Failed to fetch user data:', err);
    }
}

// Render cards in container
function renderCards() {
    cardsContainer.innerHTML = '';
    cards.forEach((card, index) => {
        const div = document.createElement('div');
        div.className = 'card';
        div.innerHTML = `
            <input type="text" value="**** **** **** ${card.cardNumber.slice(-4)}" readonly>
            <input type="text" value="${card.expirationDate}" readonly>
            <input type="text" value="${card.billingAddress}" readonly>
            <button type="button" class="removeCardBtn" data-index="${index}">Remove</button>
        `;
        cardsContainer.appendChild(div);
    });

    document.querySelectorAll('.removeCardBtn').forEach(btn => {
        btn.addEventListener('click', e => {
            const i = e.target.dataset.index;
            cards.splice(i, 1);
            renderCards();
        });
    });
}

// Add card
addCardBtn.addEventListener('click', () => {
    if (cards.length >= 3) {
        alert('You can only store up to 3 cards.');
        return;
    }
    const cardNumber = prompt('Enter card number (16 digits):');
    const expiration = prompt('Enter expiration date (MM/YY):');
    const billing = prompt('Enter billing address:');

    if (!cardNumber || cardNumber.length !== 16 || !/^\d+$/.test(cardNumber)) {
        alert('Invalid card number.');
        return;
    }
    if (!expiration || !billing) {
        alert('Expiration date and billing address required.');
        return;
    }

    cards.push({ cardNumber, expirationDate: expiration, billingAddress: billing });
    renderCards();
});

// Submit form
form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const updatedUser = {
        email: user.email,
        firstName: document.getElementById('firstName').value.trim(),
        lastName: document.getElementById('lastName').value.trim(),
        street: document.getElementById('street').value.trim(),
        city: document.getElementById('city').value.trim(),
        state: document.getElementById('state').value.trim(),
        zipCode: document.getElementById('zip').value.trim(),
        promotions: document.getElementById('promotions').checked
    };

    try {
        // Update profile info
        const res = await fetch('http://localhost:8080/user/edit-profile', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(updatedUser)
        });
        if (!res.ok) throw new Error(await res.text());

        // Update cards
        await fetch(`http://localhost:8080/user/edit-cards?email=${encodeURIComponent(user.email)}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(cards)
        });

        statusMessage.textContent = "Profile updated successfully!";
    } catch (err) {
        statusMessage.textContent = err.message;
    }
});

// Navigate back
document.getElementById('returnProfileBtn').addEventListener('click', () => {
    window.location.href = '../profile/profile.html';
});

// Initialize
populateForm();
