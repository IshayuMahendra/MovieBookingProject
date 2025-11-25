// book/seat-map.js

const API_BASE = "http://localhost:8080"; 

const params = new URLSearchParams(window.location.search);
const bookingId = params.get("bookingId");
const ticketCount = parseInt(params.get("tickets") || "1", 10);
const movieTitle = params.get("movie") || "Unknown";
const showtimeText = params.get("time") || "Unknown";


document.getElementById("movieTitle").textContent = movieTitle;
document.getElementById("showtime").textContent = showtimeText;
document.getElementById("ticketCount").textContent = ticketCount;

const seatMapContainer = document.getElementById("seat-map");
const confirmBtn = document.getElementById("confirmSeatsBtn");
const messageEl = document.getElementById("message");

let seatMap = [];
let selectedSeatIds = [];


async function loadSeatMap() {
  if (!bookingId) {
    messageEl.textContent = "Missing booking ID.";
    return;
  }

  try {
    const res = await fetch(`${API_BASE}/api/bookings/${bookingId}/seat-map`);
    if (!res.ok) {
      const text = await res.text();
      throw new Error(`HTTP ${res.status}: ${text}`);
    }
    seatMap = await res.json();
    renderSeatMap();
  } catch (err) {
    console.error(err);
    messageEl.textContent = "Error loading seats: " + err.message;
  }
}

function renderSeatMap() {
  seatMapContainer.innerHTML = "";

  // group by row
  const rows = {};
  seatMap.forEach((seat) => {
    if (!rows[seat.row]) rows[seat.row] = [];
    rows[seat.row].push(seat);
  });

  const sortedRowKeys = Object.keys(rows).sort(); 

  sortedRowKeys.forEach((rowKey) => {
    const rowSeats = rows[rowKey].sort((a, b) => a.number - b.number);

    const rowDiv = document.createElement("div");
    rowDiv.classList.add("seat-row");

    const label = document.createElement("div");
    label.classList.add("row-label");
    label.textContent = rowKey;
    rowDiv.appendChild(label);

    rowSeats.forEach((seat) => {
      const seatDiv = document.createElement("div");
      seatDiv.classList.add("seat");
      seatDiv.textContent = seat.number;
      seatDiv.dataset.id = seat.id;

      if (seat.available) {
        seatDiv.classList.add("available");
        seatDiv.addEventListener("click", () =>
          toggleSeatSelection(seat.id, seatDiv)
        );
      } else {
        seatDiv.classList.add("taken");
      }

      rowDiv.appendChild(seatDiv);
    });

    seatMapContainer.appendChild(rowDiv);
  });
}

function toggleSeatSelection(seatId, element) {
  const index = selectedSeatIds.indexOf(seatId);

  if (index === -1) {
    
    if (selectedSeatIds.length >= ticketCount) {
      messageEl.textContent = `You can only select ${ticketCount} seat(s).`;
      return;
    }
    selectedSeatIds.push(seatId);
    element.classList.add("selected");
  } else {
    
    selectedSeatIds.splice(index, 1);
    element.classList.remove("selected");
  }

  if (!messageEl.textContent.startsWith("Error")) {
    messageEl.textContent = "";
  }
}

async function confirmSeats() {
  if (selectedSeatIds.length !== ticketCount) {
    messageEl.textContent = `Please select exactly ${ticketCount} seat(s).`;
    return;
  }

  try {
    const res = await fetch(
      `${API_BASE}/api/bookings/${bookingId}/select-seats`,
      {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ seatIds: selectedSeatIds }),
      }
    );

    if (!res.ok) {
      const text = await res.text();
      throw new Error(`HTTP ${res.status}: ${text}`);
    }

    const data = await res.json(); 
    messageEl.textContent = "Seats confirmed! Status: " + data.status;
    
  } catch (err) {
    console.error(err);
    messageEl.textContent = "Error confirming seats: " + err.message;
  }
}

confirmBtn.addEventListener("click", confirmSeats);
loadSeatMap();
