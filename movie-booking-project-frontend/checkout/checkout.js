const API_BASE = "http://localhost:8080";

// Get booking info and user info from sessionStorage
const bookingId = sessionStorage.getItem("currentBookingId");
let loggedInUserId = sessionStorage.getItem("currentUserId"); // MongoDB ObjectId string
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
const confirmBtn = document.getElementById("confirmOrderBtn");
const messageEl = document.getElementById("message");

// Redirect to login if booking or user info is missing
if (!bookingId || !loggedInUser) {
  const redirectUrl = encodeURIComponent(window.location.href);
  window.location.href = `../login/login.html?redirect=${redirectUrl}`;
}

// Fetch user ObjectId if missing
async function ensureUserId() {
  if (!loggedInUserId) {
    try {
      const res = await fetch(`${API_BASE}/user/by-email?email=${encodeURIComponent(loggedInUser.email)}`);
      if (!res.ok) throw new Error(await res.text());
      const data = await res.json();
      loggedInUserId = data._id;
      sessionStorage.setItem("currentUserId", loggedInUserId);
    } catch (err) {
      console.error(err);
      messageEl.textContent = "Failed to get user info: " + err.message;
    }
  }
}

// --- Load checkout info ---
async function loadCheckout() {
  await ensureUserId();
  try {
    const res = await fetch(`${API_BASE}/checkout/${bookingId}?userId=${encodeURIComponent(loggedInUserId)}`);
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

// --- Confirm order ---
async function confirmOrder() {
  await ensureUserId();
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
    const res = await fetch(`${API_BASE}/checkout/${bookingId}/confirm?userId=${encodeURIComponent(loggedInUserId)}`, {
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
