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


function getFilteredMoviesWithDate(title, genre, date) { //date is in form mm/dd/yyyy
    let filteredMovies = []
    fetch(`http://localhost:8080/movies/${title}/${genre}/${date}`)
        .then((response) => response.json())
        .then((data) => {
            filteredMovies = data
            console.log(filteredMovies)
        })
        .catch((err) => console.error('Error:', err))
    return filteredMovies
}

function getRunningMovies() { //under the "now showing" part of the web page. gets movies with running: true
    let runningMovies = []
    fetch(`http://localhost:8080/movies/running`)
        .then((response) => response.json())
        .then((data) => {
            runningMovies = data
            console.log(runningMovies)
        })
        .catch((err) => console.error('Error:', err))
    return runningMovies
}

function getComingSoonMovies() { //under the "coming soon" part of the web page. gets movies with running: false
    let comingSoonMovies = []
    fetch(`http://localhost:8080/movies/coming-soon`)
        .then((response) => response.json())
        .then((data) => {
            comingSoonMovies = data
            console.log(comingSoonMovies)
        })
        .catch((err) => console.error('Error:', err))
    return comingSoonMovies
}
