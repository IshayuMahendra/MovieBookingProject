
const API_BASE = "http://localhost:8080";

// Get booking info and user info from sessionStorage
const bookingId = sessionStorage.getItem("currentBookingId");

const loggedInUser = JSON.parse(sessionStorage.getItem("loggedInUser"));

const bookingIdEl = document.getElementById("bookingId");
const subtotalEl = document.getElementById("subtotal");
const discountEl = document.getElementById("discount");
const totalEl = document.getElementById("total");

const savedCardsContainer = document.getElementById("savedCardsContainer");

const cardNumberEl = document.getElementById("cardNumber");
const expirationEl = document.getElementById("expirationDate");
const billingEl = document.getElementById("billingAddress");
const saveCardEl = document.getElementById("saveCard");

const promoCodeEl = document.getElementById("promoCode");
const applyPromoBtn = document.getElementById("applyPromoBtn");
const confirmBtn = document.getElementById("confirmOrderBtn");
const messageEl = document.getElementById("message");

// Redirect to login if booking or user info is missing
if (!bookingId || !loggedInUser) {
  const redirectUrl = encodeURIComponent(window.location.href);
  window.location.href = `../login/login.html?redirect=${redirectUrl}`;
}
console.log("bookingId from sessionStorage:", bookingId);
console.log("loggedInUser from sessionStorage:", loggedInUser);

// --- Load checkout info ---
async function loadCheckout() {
  
  try {
     const res = await fetch(
      `${API_BASE}/checkout/${bookingId}?email=${encodeURIComponent(loggedInUser.email)}`
    );
    if (!res.ok) throw new Error(await res.text());
    const data = await res.json();

    bookingIdEl.textContent = data.bookingId;
    subtotalEl.textContent = data.total.toFixed(2);
    discountEl.textContent = "0.00";
    totalEl.textContent = data.total.toFixed(2);

    savedCardsContainer.innerHTML = "";
    data.savedCards.forEach(card => {
      const div = document.createElement("div");
      div.innerHTML = `
        <input type="radio" name="savedCard" value="${card.cardId}">
        ${card.maskedNumber} (exp: ${card.expirationDate})
      `;
      savedCardsContainer.appendChild(div);
    });
  } catch (err) {
    console.error(err);
    messageEl.textContent = "Failed to load checkout: " + err.message;
  }
}

async function applyPromo() {
  messageEl.textContent = "";
  const code = promoCodeEl.value.trim(); // this is your long ObjectId string

  if (!code) {
    messageEl.textContent = "Please enter a promotion code.";
    return;
  }

  try {
    const url = `${API_BASE}/checkout/${bookingId}/preview?email=${encodeURIComponent(
      loggedInUser.email
    )}&promotionCode=${encodeURIComponent(code)}`;

    console.log("Applying promo with URL:", url);

    const res = await fetch(url);
    console.log("GET /preview status:", res.status);

    if (!res.ok) {
      const text = await res.text();
      throw new Error(text);
    }

    const data = await res.json();
    console.log("Preview totals:", data);

    // Update displayed prices
    subtotalEl.textContent = data.subtotal.toFixed(2);
    discountEl.textContent = data.discount.toFixed(2);
    totalEl.textContent = data.total.toFixed(2);

  } catch (err) {
    console.error("Error applying promo:", err);
    messageEl.textContent = "Failed to apply promo: " + err.message;
  }
}

applyPromoBtn.addEventListener("click", applyPromo);
// --- Confirm order ---
async function confirmOrder() {
  
  messageEl.textContent = "";

  let savedCardId = null;
  const radios = document.querySelectorAll('input[name="savedCard"]:checked');
  if (radios.length > 0) savedCardId = radios[0].value;

  const requestBody = {
    savedCardId: savedCardId,
    cardNumber: cardNumberEl.value.trim(),
    expirationDate: expirationEl.value.trim(),
    billingAddress: billingEl.value.trim(),
    saveCard: saveCardEl.checked,
    promotionCode: promoCodeEl.value.trim()
  };

  try {
    const res = await fetch(`${API_BASE}/checkout/${bookingId}/confirm?email=${encodeURIComponent(loggedInUser.email)}`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(requestBody)
    });

    if (!res.ok) throw new Error(await res.text());

    const data = await res.json();

    sessionStorage.setItem("lastOrder", JSON.stringify(data));
    window.location.href = "../checkout/confirmation.html";
  } catch (err) {
    console.error(err);
    messageEl.textContent = "Failed to confirm order: " + err.message;
  }
}

confirmBtn.addEventListener("click", confirmOrder);
loadCheckout();

let timeLeft = 300; // 5 minutes in seconds
const timerEl = document.getElementById("timer");

const interval = setInterval(() => {
    timeLeft--;
    timerEl.textContent = `Time remaining: ${Math.floor(timeLeft/60)}:${timeLeft%60 < 10 ? '0'+timeLeft%60 : timeLeft%60}`;

    if (timeLeft <= 0) {
        clearInterval(interval);
        alert("Your checkout session expired. Please reselect your seats.");
        window.location.href = "../book/seat-map.html";
    }
}, 1000);