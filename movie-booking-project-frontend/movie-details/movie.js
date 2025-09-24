// retrieve the movie object from sessionStorage
const movie = JSON.parse(sessionStorage.getItem('selectedMovie'));

if (!movie) {
    document.body.innerHTML = '<p>No movie selected. Go back to the homepage.</p>';
} else {
 
    const posterEl = document.getElementById('poster');
    const titleEl = document.getElementById('title');
    const ratingEl = document.getElementById('rating');
    const descriptionEl = document.getElementById('description');
    const showtimesEl = document.getElementById('showtimes');
    const trailerEl = document.getElementById('trailer');

    //  movie details
    posterEl.src = movie.poster;
    posterEl.alt = movie.title;
    titleEl.textContent = movie.title;
    ratingEl.textContent = `Rating: ${movie.rating}%`;
    descriptionEl.textContent = movie.description;

    // render showtimes as buttons
    showtimesEl.innerHTML = '';
    movie.showtimes.forEach(time => {
        const btn = document.createElement('button');
        btn.textContent = time;
        btn.className = 'showtime-btn';

        btn.addEventListener('click', () => {
            // to booking page with query parameters
            const params = new URLSearchParams({
                movie: movie.title,
                time: time
            });
            window.location.href = `../book/booking.html?${params.toString()}`;
        });

        showtimesEl.appendChild(btn);
    });

    // YouTube trailer
    if (movie.trailer) {
        // convert YouTube URL to embed iframe URL
        const embedUrl = movie.trailer.replace("watch?v=", "embed/");
        trailerEl.innerHTML = `
            <iframe 
                width="100%" 
                height="450" 
                src="${embedUrl}" 
                title="${movie.title} Trailer"
                frameborder="0"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                allowfullscreen>
            </iframe>
        `;
    }
}
