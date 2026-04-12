const API_BASE_URL = '/api/v1';

// Tab navigation
document.querySelectorAll('.tab-button').forEach(button => {
    button.addEventListener('click', () => {
        const tabName = button.dataset.tab;
        switchTab(tabName);
    });
});

function switchTab(tabName) {
    // Hide all tabs
    document.querySelectorAll('.tab-content').forEach(tab => {
        tab.classList.remove('active');
    });
    
    // Remove active from all buttons
    document.querySelectorAll('.tab-button').forEach(btn => {
        btn.classList.remove('active');
    });
    
    // Show selected tab
    document.getElementById(tabName).classList.add('active');
    
    // Mark button as active
    document.querySelector(`[data-tab="${tabName}"]`).classList.add('active');
    
    // Load content for selected tab
    if (tabName === 'books') {
        loadBooks();
    } else if (tabName === 'loans') {
        loadLoans();
        loadBooksForLoan();
    } else if (tabName === 'authors') {
        loadAuthors();
    }
}

// ====== BOOKS ======

// Add Book Form
document.getElementById('bookForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const bookData = {
        title: document.getElementById('bookTitle').value,
        author: document.getElementById('bookAuthor').value,
        isbn: document.getElementById('bookISBN').value,
        publishedYear: parseInt(document.getElementById('bookYear').value),
        available: document.getElementById('bookAvailable').value === 'true'
    };
    
    try {
        const response = await fetch(`${API_BASE_URL}/books`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify([bookData])
        });
        
        if (response.ok) {
            showMessage('bookFormMessage', 'Book added successfully!', 'success');
            document.getElementById('bookForm').reset();
            loadBooks();
        } else {
            showMessage('bookFormMessage', 'Failed to add book', 'error');
        }
    } catch (error) {
        showMessage('bookFormMessage', `Error: ${error.message}`, 'error');
    }
});

async function loadBooks() {
    try {
        const response = await fetch(`${API_BASE_URL}/books`);
        const data = await response.json();
        const books = data.data || [];
        
        const booksList = document.getElementById('booksList');
        
        if (books.length === 0) {
            booksList.innerHTML = '<div class="empty-state"><p>No books found. Add one to get started!</p></div>';
            return;
        }
        
        booksList.innerHTML = books.map(book => `
            <div class="book-card ${!book.available ? 'unavailable' : ''}">
                <h3>${escapeHtml(book.title)}</h3>
                <p class="author">by ${escapeHtml(book.author)}</p>
                <p class="isbn">ISBN: ${escapeHtml(book.isbn)}</p>
                <p class="year">Published: ${book.publishedYear}</p>
                <span class="status-badge ${book.available ? 'status-available' : 'status-unavailable'}">
                    ${book.available ? '✓ Available' : '✗ Unavailable'}
                </span>
                <div class="book-actions">
                    ${book.available ? 
                        `<button class="btn btn-success" onclick="loanBook(${book.id})">Loan Book</button>` : 
                        ''
                    }
                    <button class="btn btn-info" onclick="toggleAvailability(${book.id}, ${!book.available})">
                        ${book.available ? 'Mark Unavailable' : 'Mark Available'}
                    </button>
                </div>
            </div>
        `).join('');
    } catch (error) {
        console.error('Error loading books:', error);
        document.getElementById('booksList').innerHTML = '<div class="empty-state"><p>Error loading books</p></div>';
    }
}

async function toggleAvailability(bookId, newStatus) {
    try {
        const response = await fetch(`${API_BASE_URL}/books/${bookId}/availability?available=${newStatus}`, {
            method: 'PUT'
        });
        
        if (response.ok) {
            loadBooks();
        }
    } catch (error) {
        alert(`Error updating availability: ${error.message}`);
    }
}

document.getElementById('refreshBooks').addEventListener('click', loadBooks);

// Search books
document.getElementById('bookSearch').addEventListener('input', (e) => {
    const searchTerm = e.target.value.toLowerCase();
    document.querySelectorAll('.book-card').forEach(card => {
        const text = card.textContent.toLowerCase();
        card.style.display = text.includes(searchTerm) ? '' : 'none';
    });
});

// ====== LOANS ======

async function loadBooksForLoan() {
    try {
        const response = await fetch(`${API_BASE_URL}/books`);
        const data = await response.json();
        const books = data.data || [];
        
        const select = document.getElementById('loanBookId');
        const currentValue = select.value;
        
        select.innerHTML = '<option value="">Select a book...</option>' + 
            books.filter(book => book.available)
                .map(book => `<option value="${book.id}">${escapeHtml(book.title)} - ${escapeHtml(book.author)}</option>`)
                .join('');
        
        select.value = currentValue;
    } catch (error) {
        console.error('Error loading books for loan:', error);
    }
}

document.getElementById('loanForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const bookId = parseInt(document.getElementById('loanBookId').value);
    
    if (!bookId) {
        showMessage('loanFormMessage', 'Please select a book', 'error');
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/loans`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify([{ bookId }])
        });
        
        if (response.ok) {
            showMessage('loanFormMessage', 'Loan created successfully!', 'success');
            document.getElementById('loanForm').reset();
            loadLoans();
            loadBooks();
            loadBooksForLoan();
        } else {
            showMessage('loanFormMessage', 'Failed to create loan', 'error');
        }
    } catch (error) {
        showMessage('loanFormMessage', `Error: ${error.message}`, 'error');
    }
});

async function loadLoans() {
    try {
        const response = await fetch(`${API_BASE_URL}/loans`);
        const data = await response.json();
        const loans = data.data || [];
        
        const loansList = document.getElementById('loansList');
        
        if (loans.length === 0) {
            loansList.innerHTML = '<div class="empty-state"><p>No active loans</p></div>';
            return;
        }
        
        loansList.innerHTML = loans.map(loan => `
            <div class="loan-card">
                <h3>📖 Loan #${loan.id}</h3>
                <div class="loan-detail">
                    <label>Book ID:</label>
                    <value>${loan.bookId}</value>
                </div>
                <div class="loan-detail">
                    <label>Loan Date:</label>
                    <value>${formatDate(loan.loanDate)}</value>
                </div>
                <div class="loan-detail">
                    <label>Return Date:</label>
                    <value>${loan.returnDate ? formatDate(loan.returnDate) : 'Not returned'}</value>
                </div>
                <button class="btn btn-success" onclick="returnBook(${loan.id})">Return Book</button>
            </div>
        `).join('');
    } catch (error) {
        console.error('Error loading loans:', error);
        document.getElementById('loansList').innerHTML = '<div class="empty-state"><p>Error loading loans</p></div>';
    }
}

async function returnBook(loanId) {
    if (!confirm('Mark this loan as returned?')) return;
    
    try {
        const response = await fetch(`${API_BASE_URL}/loans/${loanId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({})
        });
        
        if (response.ok) {
            loadLoans();
            loadBooks();
        } else {
            alert('Failed to return book');
        }
    } catch (error) {
        alert(`Error: ${error.message}`);
    }
}

async function loanBook(bookId) {
    document.getElementById('loanBookId').value = bookId;
    switchTab('loans');
}

document.getElementById('refreshLoans').addEventListener('click', loadLoans);

// ====== AUTHORS ======

async function loadAuthors() {
    try {
        const response = await fetch(`${API_BASE_URL}/authors`);
        const data = await response.json();
        const authors = data.data || [];
        
        const authorsList = document.getElementById('authorsList');
        
        if (authors.length === 0) {
            authorsList.innerHTML = '<div class="empty-state"><p>No authors found</p></div>';
            return;
        }
        
        authorsList.innerHTML = authors.map(author => `
            <div class="author-card">
                <h3>✍️ ${escapeHtml(author.name)}</h3>
                ${author.books && author.books.length > 0 ? `
                    <div class="author-books">
                        <h4>Books (${author.books.length})</h4>
                        <div class="book-list">
                            ${author.books.map(book => `
                                <div class="book-item">
                                    ${escapeHtml(book.title)}
                                    <span class="status-badge ${book.available ? 'status-available' : 'status-unavailable'}">
                                        ${book.available ? '✓' : '✗'}
                                    </span>
                                </div>
                            `).join('')}
                        </div>
                    </div>
                ` : '<p style="color: #999;">No books by this author</p>'}
            </div>
        `).join('');
    } catch (error) {
        console.error('Error loading authors:', error);
        document.getElementById('authorsList').innerHTML = '<div class="empty-state"><p>Error loading authors</p></div>';
    }
}

document.getElementById('refreshAuthors').addEventListener('click', loadAuthors);

// ====== UTILITIES ======

function showMessage(elementId, message, type) {
    const messageEl = document.getElementById(elementId);
    messageEl.textContent = message;
    messageEl.className = `message ${type}`;
    
    if (type === 'success') {
        setTimeout(() => {
            messageEl.className = 'message';
        }, 3000);
    }
}

function formatDate(dateString) {
    if (!dateString) return 'N/A';
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' });
}

function escapeHtml(text) {
    const map = {
        '&': '&amp;',
        '<': '&lt;',
        '>': '&gt;',
        '"': '&quot;',
        "'": '&#039;'
    };
    return text.replace(/[&<>"']/g, m => map[m]);
}

// Load initial data
loadBooks();
