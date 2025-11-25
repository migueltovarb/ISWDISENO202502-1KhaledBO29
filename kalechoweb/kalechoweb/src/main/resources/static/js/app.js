const API_URL = '/api';

// Auth
async function login(email, password) {
    const response = await fetch(`${API_URL}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
    });
    if (response.ok) {
        const user = await response.json();
        localStorage.setItem('user', JSON.stringify(user));
        window.location.href = user.role === 'ADMIN' ? 'admin.html' : 'dashboard.html';
    } else {
        alert('Login failed');
    }
}

async function register(name, email, password) {
    const response = await fetch(`${API_URL}/auth/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, email, password, role: 'STUDENT' })
    });
    if (response.ok) {
        alert('Registration successful! Please login.');
        toggleAuthMode();
    } else {
        alert('Registration failed');
    }
}

function logout() {
    localStorage.removeItem('user');
    window.location.href = 'index.html';
}

function checkAuth() {
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) {
        window.location.href = 'index.html';
    }
    return user;
}

// Rooms
async function getRooms() {
    const response = await fetch(`${API_URL}/salas`);
    return await response.json();
}

async function createRoom(room) {
    const response = await fetch(`${API_URL}/salas`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(room)
    });
    return response.ok;
}

// Reservations
async function createReservation(roomId, start, end) {
    const user = JSON.parse(localStorage.getItem('user'));
    const response = await fetch(`${API_URL}/reservas`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            userId: user.id,
            roomId: roomId,
            startTime: start,
            endTime: end
        })
    });
    if (response.ok) {
        alert('Reservation confirmed!');
        loadMyReservations();
    } else {
        const msg = await response.text();
        alert('Error: ' + msg);
    }
}

async function getMyReservations() {
    const user = JSON.parse(localStorage.getItem('user'));
    const response = await fetch(`${API_URL}/reservas/mias?userId=${user.id}`);
    return await response.json();
}

async function cancelReservation(id) {
    if (!confirm('Are you sure?')) return;
    const response = await fetch(`${API_URL}/reservas/${id}`, {
        method: 'DELETE'
    });
    if (response.ok) {
        loadMyReservations();
    }
}

async function deleteRoom(id) {
    if (!confirm('Are you sure?')) return;
    const response = await fetch(`${API_URL}/salas/${id}`, {
        method: 'DELETE'
    });
    if (response.ok) {
        alert('Room deleted');
        if (typeof loadAdminRooms === 'function') {
            loadAdminRooms();
        }
    } else {
        const msg = await response.text();
        alert('Error: ' + msg);
    }
}
