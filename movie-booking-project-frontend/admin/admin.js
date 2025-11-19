const contentArea = document.getElementById('contentArea');

// --- SESSION CHECK ---
if (!sessionStorage.getItem("loggedInAdmin")) {
    window.location.href = "../login/login.html";
}

// --- LOGOUT ---
document.getElementById("logoutBtn").addEventListener("click", () => {
    sessionStorage.removeItem("loggedInAdmin");
    window.location.href = "../login/login.html";
});

// -------------------------------
//  ADD MOVIE FORM
// -------------------------------
document.getElementById("addMovieBtn").addEventListener("click", () => {
    contentArea.innerHTML = `
        <h2>Add Movie</h2>
        <form id="movieForm">
            <label>Title</label>
            <input id="title" required>

            <label>Genres (comma separated)</label>
            <input id="genre" required>

            <label>Poster URL</label>
            <input id="poster" required>

            <label>Trailer URL</label>
            <input id="trailer" required>

            <label>Description</label>
            <textarea id="description" required></textarea>

            <label>Rating (0–100)</label>
            <input id="rating" type="number" min="0" max="100" required>

            <label>Is Running?</label>
            <select id="isRunning">
                <option value="true">Running</option>
                <option value="false">Not Running</option>
            </select>

            <button type="submit">Submit Movie</button>
        </form>
        <div id="movieResult" class="result-box"></div>
    `;

    document.getElementById("movieForm").addEventListener("submit", async (e) => {
        e.preventDefault();

        const movie = {
            title: document.getElementById("title").value,
            genre: document.getElementById("genre").value.split(",").map(g => g.trim()), // split comma-separated
            poster: document.getElementById("poster").value,
            trailer: document.getElementById("trailer").value,
            description: document.getElementById("description").value,
            rating: Number(document.getElementById("rating").value),
            isRunning: document.getElementById("isRunning").value === "true"
        };


        try {
            const res = await fetch("http://localhost:8080/admin/add-movie", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(movie)
            });

            if (res.ok) {
                const result = await res.json(); // parse backend JSON
                document.getElementById("movieResult").innerHTML =
                    `✅ Movie added successfully!<br>
             Title: <b>${result.title}</b><br>
             Rating: <b>${result.rating}</b>`;
            } else {
                const errText = await res.text();
                document.getElementById("movieResult").textContent = `❌ Error: ${errText}`;
            }
        } catch (err) {
            document.getElementById("movieResult").textContent = "❌ Movie creation failed. Try again later.";
        }
    });
});


// -------------------------------
//  VIEW SHOWROOMS
// -------------------------------
document.getElementById("viewShowroomsBtn").addEventListener("click", async () => {
    contentArea.innerHTML = `<h2>Showrooms</h2><div id="showroomList"></div>`;

    const res = await fetch("http://localhost:8080/admin/get-showrooms", {
        method: "POST"
    });

    const data = await res.json();

    let html = "<ul>";
    data.forEach(s => {
        html += `<li>
        <strong>Showroom ID:</strong> ${s.id.toString()} 
        (Theater ID: ${s.theaterID.toString()}, Seats: ${s.seatCount})
    </li>`;
    });
    html += "</ul>";
    document.getElementById("showroomList").innerHTML = html;


});


// -------------------------------
//  ADD SHOWTIME
// -------------------------------
document.getElementById("addShowtimeBtn").addEventListener("click", async () => {
    // Load showrooms first
    const showroomRes = await fetch("http://localhost:8080/admin/get-showrooms", { method: "POST" });
    const showrooms = await showroomRes.json();

    contentArea.innerHTML = `
        <h2>Add Showtime</h2>
        <form id="showtimeForm">

            <label>Movie ID</label>
            <input id="movieId" placeholder="Enter movie _id" required>

            <label>Showroom</label>
            <select id="showroomSelect">
                ${showrooms.map(s => `<option value="${s.id}">${s.name}</option>`).join("")}
            </select>

            <label>Showtime (YYYY-MM-DD HH:MM)</label>
            <input id="showtimeDate" placeholder="2025-01-10 19:30" required>

            <button type="submit">Add Showtime</button>
        </form>
        <div id="showtimeResult" class="result-box"></div>
    `;

    document.getElementById("showtimeForm").addEventListener("submit", async (e) => {
        e.preventDefault();

        const showtimeObj = {
            movieId: document.getElementById("movieId").value,
            showroom: document.getElementById("showroomSelect").value,
            dateTime: document.getElementById("showtimeDate").value
        };

        const res = await fetch("http://localhost:8080/admin/add-showtime", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(showtimeObj)
        });

        const text = await res.text();
        document.getElementById("showtimeResult").textContent = text;
    });
});


// -------------------------------
//  ADD PROMOTION
// -------------------------------
document.getElementById("addPromotionBtn").addEventListener("click", () => {
    contentArea.innerHTML = `
        <h2>Add Promotion</h2>
        <form id="promoForm">

            <label>Code</label>
            <input id="promoCode" required>

            <label>Discount %</label>
            <input id="promoDiscount" type="number" min="1" max="100" required>

            <button type="submit">Add Promotion</button>
        </form>
        <div id="promoResult" class="result-box"></div>
    `;

    document.getElementById("promoForm").addEventListener("submit", async (e) => {
        e.preventDefault();

        const promo = {
            discountPercentage: Number(document.getElementById("promoDiscount").value),
            expirationDate: null // or pick a date if you want
        };

        

        const res = await fetch("http://localhost:8080/admin/add-promotion", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(promo)
        });


        if (res.ok) {
            const result = await res.json(); // parse JSON from backend
            document.getElementById("promoResult").innerHTML =
                `✅ Promotion added successfully!<br>
         Discount: <b>${result.discountPercentage}%</b>`;
        } else {
            const errText = await res.text();
            document.getElementById("promoResult").textContent = `❌ Error: ${errText}`;
}

    });
});
