function getFilteredMovies(title, genre) {
    let filteredMovies = []
    fetch(`http://localhost:8080/movies/${title}/${genre}`)
        .then((response) => response.json())
        .then((data) => {
            filteredMovies = data
            console.log(filteredMovies)
        })
        .catch((err) => console.error('Error:', err))
    return filteredMovies
}

getFilteredMovies("the godfather", "drama")

getFilteredMovies(" ", "drama") //for some reason spring doesn't recognise empty strings so in order to
getFilteredMovies(null, "drama") //not filter by title, either use whitespace or null

getFilteredMovies("the godfather", "Any")   //use 'Any' to not filter by genre



allMovies = getFilteredMovies(null, "Any")
comingSoonMovies = []
nowPlayingMovies = []
allMovies.forEach(movie => {
    if (movie.running) {
        nowPlayingMovies.append(movie)
    } else {
        comingSoonMovies.append(movie)
    }
})