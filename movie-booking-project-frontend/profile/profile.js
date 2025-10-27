const user = JSON.parse(sessionStorage.getItem('loggedInUser'));

async function populateProfile() {
    try {
        const res = await fetch(`http://localhost:8080/user/${encodeURIComponent(user.email)}`);
        const data = await res.json();

        document.getElementById('email').textContent = data.email;
        document.getElementById('firstName').textContent = data.firstName || '';
        document.getElementById('lastName').textContent = data.lastName || '';
        document.getElementById('street').textContent = data.street || '';
        document.getElementById('city').textContent = data.city || '';
        document.getElementById('state').textContent = data.state || '';
        document.getElementById('zip').textContent = data.zipCode || '';
        document.getElementById('promotions').textContent = data.promotions ? 'Subscribed' : 'Not Subscribed';

        // Fetch cards
        const resCards = await fetch(`http://localhost:8080/user/cards?email=${encodeURIComponent(user.email)}`);
        const cards = await resCards.json();

        const cardsList = document.getElementById('cardsList');
        cardsList.innerHTML = '';
        if (cards.length > 0) {
            cards.forEach(c => {
                const li = document.createElement('li');
                li.textContent = '**** **** **** ' + c.cardNumber.slice(-4);
                cardsList.appendChild(li);
            });
        } else {
            const li = document.createElement('li');
            li.textContent = 'No cards on file';
            cardsList.appendChild(li);
        }

    } catch (err) {
        console.error('Failed to load profile:', err);
    }
}

// Navigation
document.getElementById('editProfileBtn').addEventListener('click', () => {
    window.location.href = '../editProfile/editProfile.html';
});
document.getElementById('homeBtn').addEventListener('click', () => {
    window.location.href = '../home/index.html';
});

// Initialize
populateProfile();
