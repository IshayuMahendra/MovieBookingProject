const runningContainer = document.getElementById('runningMovies');
const comingSoonContainer = document.getElementById('comingSoonMovies');
const searchInput = document.getElementById('searchInput');
const searchBtn = document.getElementById('searchBtn');
const genreFilter = document.getElementById('genreFilter');
const userControls = document.getElementById('userControls');
const loginBtn = document.getElementById('loginBtn');


// --- Update header for user or admin ---
function updateHeaderForUserOrAdmin() {
    if (!userControls) return; // prevent null errors

    const loggedInUser = JSON.parse(sessionStorage.getItem('loggedInUser'));
    const admin = JSON.parse(sessionStorage.getItem('loggedInAdmin'));

    if (admin) {
        userControls.innerHTML = `
            <span>Welcome Admin ${admin.userID}</span>
            <button id="logoutBtn">Logout</button>
            <button id="profileBtn">My Profile</button>
        `;
    } else if (loggedInUser) {
        userControls.innerHTML = `
            <span>Welcome ${loggedInUser.email}</span>
            <button id="logoutBtn">Logout</button>
            <button id="profileBtn">My Profile</button>
            <button id="orderHistoryBtn">Order History</button>
        `;

    } else {
        // Not logged in
        if (loginBtn) {
            loginBtn.addEventListener('click', () => {
                window.location.href = '../login/login.html';
            });
        }
        return;
    }

    // Common logout
    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', () => {
            sessionStorage.removeItem('loggedInUser');
            sessionStorage.removeItem('loggedInAdmin');
            location.reload();
        });
    }

    // Profile button
    const profileBtn = document.getElementById('profileBtn');
    if (profileBtn) {
        profileBtn.addEventListener('click', () => {
            window.location.href = '../profile/profile.html';
        });
    }

    // Order History button
    const orderHistoryBtn = document.getElementById('orderHistoryBtn');
    if (orderHistoryBtn) {
        orderHistoryBtn.addEventListener('click', () => {
            window.location.href = '../orderHistory/orderHistory.html';
        });
    }
}

// --- Render movies ---
function renderMovies(movies) {
    if (!runningContainer || !comingSoonContainer) return;

    runningContainer.innerHTML = '';
    comingSoonContainer.innerHTML = '';

    movies.forEach(movie => {
        const card = document.createElement('div');
        card.className = 'movie-card';

        const showtimeText = movie.showtimes && movie.showtimes.length > 0
            ? movie.showtimes.map(st => st.time).join(", ")
            : "None";

        card.innerHTML = `
            <img src="${movie.poster}" alt="${movie.title}">
            <h3>${movie.title}</h3>
            <p>Rating: ${movie.rating}%</p>
            <div class="showtimes">Showtimes: ${showtimeText}</div>
        `;

    
        card.addEventListener('click', () => {
            sessionStorage.setItem('selectedMovie', JSON.stringify(movie));
            window.location.href = '../movie-details/movie.html';
        });

        if (movie.running) {
            runningContainer.appendChild(card);
        } else {
            comingSoonContainer.appendChild(card);
        }
    });
}

// --- Fetch all movies ---
async function fetchAllMovies() {
    try {
        const res = await fetch('http://localhost:8080/movies');
        const movies = await res.json();
        renderMovies(movies);
    } catch (err) {
        console.error('Failed to fetch movies:', err);
    }
}

// --- Search movies ---
async function searchMovies() {
    if (!searchInput || !genreFilter) return;

    const input = searchInput.value.trim();
    const genre = genreFilter.value;

    let url = '';
    if (!input && !genre) {
        url = 'http://localhost:8080/movies';
    } else if (!input && genre) {
        url = `http://localhost:8080/movies/genre/${encodeURIComponent(genre)}`;
    } else if (input && !genre) {
        url = `http://localhost:8080/movies/title/${encodeURIComponent(input)}`;
    } else {
        url = `http://localhost:8080/movies/${encodeURIComponent(input)}/${encodeURIComponent(genre)}`;
    }

    try {
        const res = await fetch(url);
        const movies = await res.json();
        renderMovies(movies);
    } catch (err) {
        console.error('Failed to fetch movies:', err);
    }
}


// --- Event listeners ---
if (searchBtn) searchBtn.addEventListener('click', searchMovies);
if (searchInput) {
    searchInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') searchMovies();
    });
}

// Update header on page load
updateHeaderForUserOrAdmin();

// Fetch movies on page load
fetchAllMovies();
