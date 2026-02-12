const userEmail = localStorage.getItem('userEmail');
if (!userEmail && !window.location.href.includes('login.html')) {
    window.location.href = 'login.html';
}

if (userEmail) {
    document.getElementById('user-welcome').textContent = `Manage your clients effortlessly, ${userEmail}`;
}

// State
let customers = [];
// For local development: '/api/v1/customers'
// For production: 'https://chinspring-api.onrender.com/api/v1/customers'
const apiBase = window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1'
    ? '/api/v1/customers'
    : 'https://connectinc-crm.onrender.com/api/v1/customers';

// Initialization
document.addEventListener('DOMContentLoaded', () => {
    fetchCustomers();
    lucide.createIcons();
});

// Fetching
async function fetchCustomers() {
    const loading = document.getElementById('loading-state');
    loading.style.display = 'block';

    try {
        const response = await fetch(apiBase, {
            headers: { 'X-User-Email': userEmail }
        });
        customers = await response.json();
        renderCustomers();
    } catch (error) {
        console.error('Error fetching customers:', error);
    } finally {
        loading.style.display = 'none';
    }
}

// Rendering
function renderCustomers() {
    const grid = document.getElementById('customer-grid');
    grid.innerHTML = '';

    customers.forEach(c => {
        const isOverdue = c.nextFollowUp && new Date(c.nextFollowUp) < new Date();
        const card = document.createElement('div');
        card.className = `card priority-${c.priority.toLowerCase()} ${isOverdue ? 'overdue-alert' : ''}`;

        const interactionsHtml = c.interactions && c.interactions.length > 0
            ? c.interactions.slice(-2).reverse().map(int => `
                <div class="timeline-item">
                    <div class="timeline-dot"></div>
                    <div class="timeline-content">
                        <span class="timeline-type">${int.type}</span>
                        <p>${int.note}</p>
                    </div>
                </div>
            `).join('')
            : '<p style="font-size: 0.75rem; color: var(--text-secondary); font-style: italic;">No history yet.</p>';

        card.innerHTML = `
            <div class="customer-header">
                <div>
                    <span class="customer-name">${c.name}</span>
                    <div style="font-size: 0.7rem; color: var(--text-secondary); display: flex; align-items: center; gap: 4px; margin-top: 4px;">
                        <i data-lucide="clock" style="width: 12px; height: 12px;"></i> ID: ${c.id}
                    </div>
                </div>
                <span class="badge badge-${c.status.toLowerCase()}">${c.status}</span>
            </div>

            <div class="customer-info"><i data-lucide="user" style="width: 16px; height: 16px;"></i> ${c.age} years old</div>
            <div class="customer-info"><i data-lucide="mail" style="width: 16px; height: 16px;"></i> ${c.email}</div>
            ${c.nextFollowUp ? `
                <div class="customer-info ${isOverdue ? 'overdue-text' : ''}">
                    <i data-lucide="calendar" style="width: 16px; height: 16px;"></i> Follow-up: ${new Date(c.nextFollowUp).toLocaleDateString()}
                    ${isOverdue ? '<span style="margin-left: 4px; font-size: 0.7rem; font-weight: bold;">(OVERDUE)</span>' : ''}
                </div>
            ` : ''}

            <div class="interaction-history">
                <div style="display: flex; align-items: center; gap: 8px; margin-bottom: 12px; font-size: 0.85rem; font-weight: 600;">
                    <i data-lucide="history" style="width: 14px; height: 14px;"></i> Timeline
                </div>
                <div class="timeline">
                    ${interactionsHtml}
                </div>
            </div>

            <div class="actions">
                <button class="btn-outline" onclick="showInteractionModal(${c.id})" title="Log Interaction">
                    <i data-lucide="message-square" style="width: 16px; height: 16px;"></i>
                </button>
                <button class="btn-outline" title="Edit Client"><i data-lucide="edit-2" style="width: 16px; height: 16px;"></i></button>
                <button class="btn-danger" onclick="deleteCustomer(${c.id})" title="Delete Client"><i data-lucide="trash-2" style="width: 16px; height: 16px;"></i></button>
            </div>
        `;
        grid.appendChild(card);
    });

    // Process icons for the newly added elements
    lucide.createIcons();
}

// Modal Logic
function showRegisterModal() {
    document.getElementById('register-modal').style.display = 'flex';
}

function hideRegisterModal() {
    document.getElementById('register-modal').style.display = 'none';
}

function showInteractionModal(id) {
    document.getElementById('interaction-customer-id').value = id;
    document.getElementById('interaction-modal').style.display = 'flex';
}

function hideInteractionModal() {
    document.getElementById('interaction-modal').style.display = 'none';
}

// Form Hansling
document.getElementById('register-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData.entries());

    // Add time for Spring Boot compatibility if date present
    if (data.nextFollowUp) {
        data.nextFollowUp += "T00:00:00";
    }

    try {
        await fetch(apiBase, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-User-Email': userEmail
            },
            body: JSON.stringify(data)
        });
        hideRegisterModal();
        e.target.reset();
        fetchCustomers();
    } catch (error) {
        console.error('Error adding customer:', error);
    }
});

document.getElementById('interaction-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = document.getElementById('interaction-customer-id').value;
    const note = document.getElementById('interaction-note').value;
    const type = document.getElementById('interaction-type').value;

    try {
        await fetch(`${apiBase}/${id}/interactions`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-User-Email': userEmail
            },
            body: JSON.stringify({ note, type })
        });
        hideInteractionModal();
        e.target.reset();
        fetchCustomers();
    } catch (error) {
        console.error('Error adding interaction:', error);
    }
});

async function deleteCustomer(id) {
    if (confirm('Are you sure you want to delete this client?')) {
        try {
            await fetch(`${apiBase}/${id}`, {
                method: 'DELETE',
                headers: { 'X-User-Email': userEmail }
            });
            fetchCustomers();
        } catch (error) {
            console.error('Error deleting customer:', error);
        }
    }
}

function logout() {
    localStorage.removeItem('userEmail');
    window.location.href = 'login.html';
}
