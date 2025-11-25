const API_BASE = "http://localhost:8080"; 


const params = new URLSearchParams(window.location.search);

const movieTitle = params.get("movie") || "Unknown";
const showtimeText = params.get("time") || "Unknown";
const showId = params.get("showId");  

document.getElementById("movieTitle").textContent = movieTitle;
document.getElementById("showtime").textContent = showtimeText;


const nameInput = document.getElementById("name");
const emailInput = document.getElementById("email");
const ticketType1Input = document.getElementById("ticketType1");
const ticketType2Input = document.getElementById("ticketType2");
const ticketType3Input = document.getElementById("ticketType3");
const confirmBtn = document.getElementById("confirmBtn");
const seatPreviewContainer = document.getElementById("seatPreview");


confirmBtn.addEventListener("click", async () => {
    clearErrors();

    const name = nameInput.value.trim();
    const email = emailInput.value.trim();
    const type1Count = parseInt(ticketType1Input.value, 10) || 0;
    const type2Count = parseInt(ticketType2Input.value, 10) || 0;
    const type3Count = parseInt(ticketType3Input.value, 10) || 0;

    const totalTickets = type1Count + type2Count + type3Count;

    let hasError = false;
    
    if (!showId) {
        setError("nameError", "Please enter your name.");
        hasError = true;
    }
    if (!name) {
        setError("nameError", "Please enter your name.");
        hasError = true;
    }
    if (!email) {
        setError("emailError", "Please enter your email.");
        hasError = true;
    }
    if (totalTickets < 1) {
        setError("ticketsError", "Enter at least one ticket.");
        hasError = true;
    }

    if (hasError) {
        return;
    }


     const ticketRequests = [];

    for (let i = 0; i < type1Count; i++) {
        ticketRequests.push({ ageCategory: "ADULT" });
    }
    for (let i = 0; i < type2Count; i++) {
        ticketRequests.push({ ageCategory: "CHILD" });
    }
    for (let i = 0; i < type3Count; i++) {
        ticketRequests.push({ ageCategory: "SENIOR" });
    }

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
        `&tickets=${totalTickets}` +
        `&movie=${encodeURIComponent(movieTitle)}` +
        `&time=${encodeURIComponent(showtimeText)}`;
    } catch (err) {
        console.error(err);
        alert("Error: " + err.message);
    }

});

function setError(id, message) {
    const el = document.getElementById(id);
    el.textContent = message;
    el.style.display = "block";
}

function clearErrors() {
    document.querySelectorAll('.error-message').forEach(el => {
        el.style.display = "none";
    });
}


async function loadSeatPreview() {
    if (!showId || !seatPreviewContainer) return;

    try {
        const res = await fetch(`${API_BASE}/api/shows/${showId}/seat-map`);
        if (!res.ok) {
            console.error("Failed to load seat preview", await res.text());
            return;
        }
        const seatMap = await res.json();
        renderSeatPreview(seatMap);
    } catch (err) {
        console.error("Error loading seat preview:", err);
    }
}

function renderSeatPreview(seatMap) {
    seatPreviewContainer.innerHTML = "";

    const rows = {};
    seatMap.forEach(seat => {
        if (!rows[seat.row]) rows[seat.row] = [];
        rows[seat.row].push(seat);
    });

    const sortedRows = Object.keys(rows).sort();

    sortedRows.forEach(rowKey => {
        const rowSeats = rows[rowKey].sort((a, b) => a.number - b.number);

        const rowDiv = document.createElement("div");
        rowDiv.classList.add("seat-row");

        const label = document.createElement("div");
        label.classList.add("row-label");
        label.textContent = rowKey;
        rowDiv.appendChild(label);

        rowSeats.forEach(seat => {
            const seatDiv = document.createElement("div");
            seatDiv.classList.add("seat");
            seatDiv.textContent = seat.number;

            if (seat.available) {
                seatDiv.classList.add("available");
            } else {
                seatDiv.classList.add("taken");
            }

            rowDiv.appendChild(seatDiv);
        });

        seatPreviewContainer.appendChild(rowDiv);
    });
}

// load preview on page load
loadSeatPreview();