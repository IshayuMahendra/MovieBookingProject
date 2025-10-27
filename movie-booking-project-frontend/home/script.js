const runningContainer = document.getElementById('runningMovies');
const comingSoonContainer = document.getElementById('comingSoonMovies');
const searchInput = document.getElementById('searchInput');
const searchBtn = document.getElementById('searchBtn');
const genreFilter = document.getElementById('genreFilter');

// render movies to the page

const userControls = document.getElementById('userControls');
const loginBtn = document.getElementById('loginBtn');

// Check if user is logged in
const loggedInUser = JSON.parse(sessionStorage.getItem('loggedInUser'));

const admin = JSON.parse(sessionStorage.getItem('loggedInAdmin'));
if (admin) {
    document.getElementById('welcomeMessage').textContent = `Welcome Admin ${admin.userID}`;
}


function updateHeaderForLogin(user) {
    userControls.innerHTML = `
        <span>Welcome</span>
        <button id="logoutBtn">Logout</button>
        <button id="profileBtn">My Profile</button>
    `;

    document.getElementById('logoutBtn').addEventListener('click', () => {
        sessionStorage.removeItem('loggedInUser');
        location.reload(); // reload page to show login button again
    });

    document.getElementById('profileBtn').addEventListener('click', () => {
        window.location.href = '../profile/profile.html';
    });
}

// Update header based on login state
if (loggedInUser) {
    updateHeaderForLogin(loggedInUser);
} else {
    loginBtn.addEventListener('click', () => {
        window.location.href = '../login/login.html';
    });
}

function renderMovies(movies) {
    runningContainer.innerHTML = '';
    comingSoonContainer.innerHTML = '';

    movies.forEach(movie => {
        const card = document.createElement('div');
        card.className = 'movie-card';
        card.innerHTML = `
            <img src="${movie.poster}" alt="${movie.title}">
            <h3>${movie.title}</h3>
            <p>Rating: ${movie.rating}%</p>
            <div class="showtimes">Showtimes: ${movie.showtimes.join(', ')}</div>
        `;

        // to go to Movie Details
        card.addEventListener('click', () => {
            // clicked movie in sessionStorage
            sessionStorage.setItem('selectedMovie', JSON.stringify(movie));
            // to movie details page
            window.location.href = '../movie-details/movie.html';
        });


        if (movie.running) {
            runningContainer.appendChild(card);
        } else {
            comingSoonContainer.appendChild(card);
        }
    });
}

// get all movies 
async function fetchAllMovies() {
    try {
        const res = await fetch('http://localhost:8080/movies');
        const movies = await res.json();
        renderMovies(movies);
    } catch (err) {
        console.error('Failed to fetch movies:', err);
    }
}

// search  triggered by GO button
async function searchMovies() {
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

// event listeners
searchBtn.addEventListener('click', searchMovies);

//  enter to trigger search
searchInput.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') searchMovies();
});


loginBtn.addEventListener('click', () => {
    window.location.href = '../login/login.html';
});




// fetch all
fetchAllMovies();
