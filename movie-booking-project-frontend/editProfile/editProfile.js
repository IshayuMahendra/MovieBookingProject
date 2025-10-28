/* =================== ELEMENTS =================== */
const emailInput = document.getElementById('email');
const firstNameInput = document.getElementById('firstName');
const lastNameInput = document.getElementById('lastName');
const streetInput = document.getElementById('street');
const cityInput = document.getElementById('city');
const stateInput = document.getElementById('state');
const zipInput = document.getElementById('zip');
const promotionsInput = document.getElementById('promotions');
const currentPasswordInput = document.getElementById('currentPassword');
const newPasswordInput = document.getElementById('newPassword');
const confirmPasswordInput = document.getElementById('confirmPassword');


const cardsContainer = document.getElementById('cardsContainer');
const addCardBtn = document.getElementById('addCardBtn');
const editProfileForm = document.getElementById('editProfileForm');
const statusMessage = document.getElementById('statusMessage');
const returnProfileBtn = document.getElementById('returnProfileBtn');

/* =================== SESSION =================== */
const loggedInUser = JSON.parse(sessionStorage.getItem('loggedInUser'));
const admin = JSON.parse(sessionStorage.getItem('loggedInAdmin'));

/* =================== EMAIL FIELD =================== */
if (admin && emailInput) {
    emailInput.disabled = false;
    emailInput.value = admin.userID; // admin email/ID stored in session
} else if (loggedInUser && emailInput) {
    emailInput.disabled = true;
    emailInput.value = loggedInUser.email;
}

/* =================== LOAD PROFILE =================== */
async function loadProfile() {
    const email = emailInput.value;
    try {
        const res = await fetch(`http://localhost:8080/user/${encodeURIComponent(email)}`);
        if (!res.ok) throw new Error('Failed to load profile');
        const data = await res.json();

        firstNameInput.value = data.firstName || '';
        lastNameInput.value = data.lastName || '';
        streetInput.value = data.street || '';
        cityInput.value = data.city || '';
        stateInput.value = data.state || '';
        zipInput.value = data.zipCode || '';
        promotionsInput.checked = !!data.promotions;

        // Load payment cards
        loadCards(email);
    } catch (err) {
        console.error(err);
        if (statusMessage) statusMessage.textContent = 'Failed to load profile';
    }
}

/* =================== PAYMENT CARDS =================== */
async function loadCards(email) {
    try {
        const res = await fetch(`http://localhost:8080/user/cards?email=${encodeURIComponent(email)}`);
        if (!res.ok) throw new Error('Failed to fetch cards');
        const cards = await res.json();
        renderCards(cards);
    } catch (err) {
        console.error(err);
    }
}

function renderCards(cards) {
    if (!cardsContainer) return;
    cardsContainer.innerHTML = '';

    cards.forEach(card => {
        const div = document.createElement('div');
        div.className = 'card-entry';
        div.innerHTML = `
            <input type="text" class="cardNumber" placeholder="Card Number" value="${card.cardNumber}">
            <input type="text" class="expirationDate" placeholder="MM/YY" value="${card.expirationDate}">
            <input type="text" class="billingAddress" placeholder="Billing Address" value="${card.billingAddress}">
            <button type="button" class="deleteCardBtn">Delete</button>
        `;
        div.querySelector('.deleteCardBtn').addEventListener('click', () => div.remove());
        cardsContainer.appendChild(div);
    });
}

if (addCardBtn) {
    addCardBtn.addEventListener('click', () => {
        if (!cardsContainer) return;
        const currentCards = cardsContainer.querySelectorAll('.card-entry').length;
        if (currentCards >= 3) return alert('Max 3 cards allowed');
        const div = document.createElement('div');
        div.className = 'card-entry';
        div.innerHTML = `
            <input type="text" class="cardNumber" placeholder="Card Number">
            <input type="text" class="expirationDate" placeholder="MM/YY">
            <input type="text" class="billingAddress" placeholder="Billing Address">
            <button type="button" class="deleteCardBtn">Delete</button>
        `;
        div.querySelector('.deleteCardBtn').addEventListener('click', () => div.remove());
        cardsContainer.appendChild(div);
    });
}



/* =================== SAVE CHANGES =================== */
if (editProfileForm) {
    editProfileForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        if (!emailInput) return;

        const email = emailInput.value.trim();
        const payload = {
            firstName: firstNameInput.value.trim(),
            lastName: lastNameInput.value.trim(),
            street: streetInput.value.trim(),
            city: cityInput.value.trim(),
            state: stateInput.value.trim(),
            zipCode: zipInput.value.trim(),
            promotions: promotionsInput.checked,
            email: email
        };

        // Collect payment cards
        const cards = [];
        if (cardsContainer) {
            cardsContainer.querySelectorAll('.card-entry').forEach(cardDiv => {
                const cardNumber = cardDiv.querySelector('.cardNumber')?.value.trim();
                const expirationDate = cardDiv.querySelector('.expirationDate')?.value.trim();
                const billingAddress = cardDiv.querySelector('.billingAddress')?.value.trim();
                if (cardNumber && expirationDate && billingAddress) {
                    cards.push({ cardNumber, expirationDate, billingAddress });
                }
            });
        }

        try {
            // Update profile (POST, not PUT)
            const resProfile = await fetch(`http://localhost:8080/user/edit-profile`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });
            if (!resProfile.ok) throw new Error('Failed to update profile');

            // Update cards
            const resCards = await fetch(`http://localhost:8080/user/edit-cards?email=${encodeURIComponent(email)}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(cards)
            });
            if (!resCards.ok) throw new Error('Failed to update cards');

            // =================== CHANGE PASSWORD ===================
            const currentPassword = currentPasswordInput?.value.trim();
            const newPassword = newPasswordInput?.value.trim();
            const confirmPassword = confirmPasswordInput?.value.trim();

            if (currentPassword || newPassword || confirmPassword) {

                if (!currentPassword || !newPassword || !confirmPassword) {
                    statusMessage.textContent = 'Fill in all password fields to change password';
                    return;
                }

                if (newPassword !== confirmPassword) {
                    if (statusMessage) statusMessage.textContent = 'New password and confirm password do not match';
                    return;
                }

                try {
                    const resPwd = await fetch('http://localhost:8080/user/change-password', {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({
                            email: email,
                            currentPassword: currentPassword,
                            newPassword: newPassword
                        })
                    });
                    const text = await resPwd.text();
                    if (!resPwd.ok) throw new Error(text);
                    if (statusMessage) statusMessage.textContent = 'Profile and password updated successfully!';
                } catch (err) {
                    console.error(err);
                    if (statusMessage) statusMessage.textContent = 'Error changing password: ' + err.message;
                }
            }


            if (statusMessage) statusMessage.textContent = 'Profile updated successfully!';
        } catch (err) {
            console.error(err);
            if (statusMessage) statusMessage.textContent = 'Error updating profile';
        }
    });
}

/* =================== RETURN BUTTON =================== */
if (returnProfileBtn) {
    returnProfileBtn.addEventListener('click', () => {
        window.location.href = '../profile/profile.html';
    });
}

/* =================== INITIAL LOAD =================== */
loadProfile();
