/* =================== ELEMENTS =================== */
const orderHistorySection = document.getElementById('orderHistorySection');
const orderHistoryTableBody = document.querySelector('#orderHistoryTable tbody');

async function fetchOrderHistory() {
    const loggedInUser = JSON.parse(sessionStorage.getItem('loggedInUser'));
    if (!loggedInUser || !loggedInUser.email) {
        console.error('No valid user email found in sessionStorage');
        return;
    }

    try {
        const res = await fetch(`http://localhost:8080/api/bookings/email/${encodeURIComponent(loggedInUser.email)}/order-history`);
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        const bookings = await res.json();

        if (bookings.length === 0) {
            orderHistoryTableBody.innerHTML = '<tr><td colspan="6">No previous orders found.</td></tr>';
        } else {
            orderHistoryTableBody.innerHTML = '';
            bookings.forEach(b => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${b.bookingID}</td>
                    <td>${b.movieTitle}</td>
                    <td>${b.showTime}</td>
                    <td>${b.ticketCount}</td>
                    <td>$${b.total}</td>
                `;
                orderHistoryTableBody.appendChild(row);
            });
        }

        orderHistorySection.style.display = 'block';
    } catch (err) {
        console.error('Failed to fetch order history:', err);
    }
}

// fetch order history on load
fetchOrderHistory();
