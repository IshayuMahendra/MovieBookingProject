const API_BASE = "http://localhost:8080"; 


const params = new URLSearchParams(window.location.search);

const movieTitle = params.get("movie") || "Unknown";
const showtimeText = params.get("time") || "Unknown";
const showId = params.get("showId");  

document.getElementById("movieTitle").textContent = movieTitle;
document.getElementById("showtime").textContent = showtimeText;


const nameInput = document.getElementById("name");
const emailInput = document.getElementById("email");
const ticketsInput = document.getElementById("tickets");
const confirmBtn = document.getElementById("confirmBtn");


confirmBtn.addEventListener("click", async () => {
    const name = nameInput.value.trim();
    const email = emailInput.value.trim();
    const tickets = parseInt(ticketsInput.value, 10);

    
    if (!showId) {
        alert("Missing showtime ID.");
        return;
    }
    if (!name) {
        alert("Please enter your name.");
        return;
    }
    if (!email) {
        alert("Please enter your email.");
        return;
    }
    if (!tickets || tickets < 1) {
        alert("Enter a valid number of tickets.");
        return;
    }

   
    const ticketRequests = Array.from({ length: tickets }, () => ({
        ageCategory: "ADULT"
    }));

    const requestBody = {
        showId: showId,
        tickets: ticketRequests
    };

    try {
        const res = await fetch(`${API_BASE}/api/bookings/start`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(requestBody)
        });

        if (!res.ok) {
            const errorText = await res.text();
            throw new Error(errorText || "Error starting booking");
        }

        const data = await res.json(); 
        const bookingId = data.bookingId;

        
        window.location.href =
            `seat-map.html?bookingId=${bookingId}` +
            `&tickets=${tickets}` +
            `&movie=${encodeURIComponent(movieTitle)}` +
            `&time=${encodeURIComponent(showtimeText)}`;
    } catch (err) {
        console.error(err);
        alert("Error: " + err.message);
    }
});