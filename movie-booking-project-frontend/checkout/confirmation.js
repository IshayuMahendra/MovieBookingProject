console.log("Confirmation page loaded");

const lastOrder = JSON.parse(sessionStorage.getItem("lastOrder"));

if (!lastOrder) {
    // If there is no last order in sessionStorage, redirect to home
    window.location.href = "../home/index.html";
}

document.getElementById("bookingId").textContent = lastOrder.bookingId;
document.getElementById("subtotal").textContent = lastOrder.subtotal.toFixed(2);
document.getElementById("discount").textContent = lastOrder.discount.toFixed(2);
document.getElementById("total").textContent = lastOrder.total.toFixed(2);

// Return home button
document.getElementById("homeBtn").addEventListener("click", () => {
    window.location.href = "../home/index.html";
});
