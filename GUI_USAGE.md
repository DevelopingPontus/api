# 📚 Book & Loan Management System - GUI

A modern, responsive web interface for managing books and tracking loans in the Book Management API.

## Features

### 📖 Books Management

- **Add New Books**: Create new book records with title, author, ISBN, publication year, and availability status
- **View All Books**: Browse all books in a beautiful card-based layout
- **Search Books**: Real-time search filtering across all books
- **Update Availability**: Toggle book availability status instantly
- **Loan Books**: Direct loan creation from book cards

### 🔄 Loans Management

- **Create Loans**: Borrow available books from the system
- **Track Active Loans**: Monitor all active loans with loan dates
- **Return Books**: Mark books as returned and restore their availability
- **Auto-populated Selection**: Only available books appear in the loan dropdown

### 👥 Authors Management

- **View All Authors**: Browse all authors in the system
- **Author Books**: See all books associated with each author
- **Availability Status**: Quick view of book availability for each author

## Getting Started

### 1. Start the Application

```bash
cd /Users/pontus/Projects/api
mvn spring-boot:run
```

The application will start at `http://localhost:8080`

### 2. Access the GUI

Open your browser and navigate to:

```
http://localhost:8080/
```

## How to Use

### Adding a Book

1. Click the **Books** tab
2. In the "Add New Book" section, fill in:
   - **Title**: Book title
   - **Author**: Author name (creates or links to existing author)
   - **ISBN**: Book ISBN number
   - **Published Year**: Year of publication
   - **Available**: Yes/No status
3. Click **Add Book**
4. The book appears in the "All Books" section

### Managing Availability

1. Go to **Books** tab
2. Find the book you want to manage
3. Click either:
   - **Loan Book**: Create a loan directly
   - **Mark Unavailable/Available**: Toggle availability status

### Creating a Loan

#### Option 1: From Book Card

1. Navigate to **Books** tab
2. Click **Loan Book** on an available book
3. You'll be switched to **Loans** tab with the book pre-selected
4. Click **Create Loan**

#### Option 2: From Loans Tab

1. Click **Loans** tab
2. Select a book from the dropdown (only available books shown)
3. Click **Create Loan**
4. The loan is created and the book becomes unavailable

### Returning a Book

1. Click **Loans** tab
2. Find the active loan you want to return
3. Click **Return Book**
4. Confirm the action
5. The book availability is automatically restored

### Searching Books

1. Click **Books** tab
2. Use the search box to filter books by:
   - Title
   - Author name
   - ISBN
   - Any other text

### Viewing Authors

1. Click **Authors** tab
2. Browse all authors in the system
3. Each author card shows:
   - Author name
   - All books by that author
   - Availability status of each book

## API Endpoints Used

The GUI communicates with these backend endpoints:

```
Books:
  POST   /api/v1/books                    - Create book
  GET    /api/v1/books                    - List all books
  GET    /api/v1/books/{id}               - Get book details
  PUT    /api/v1/books/{id}/availability  - Update book availability

Loans:
  POST   /api/v1/loans                    - Create loan
  GET    /api/v1/loans                    - List all loans
  PUT    /api/v1/loans/{id}               - Return book

Authors:
  GET    /api/v1/authors                  - List all authors
  GET    /api/v1/authors/{id}             - Get author details
```

## Interface Overview

### Navigation

- **Books Tab**: Manage book inventory
- **Loans Tab**: Track and manage loans
- **Authors Tab**: View authors and their works

### Status Indicators

- 🟢 **Available**: Book is in stock and can be loaned
- 🔴 **Unavailable**: Book is out on loan

### Button Types

- **Primary (Purple)**: Main actions (Add, Create, Update)
- **Secondary (Gray)**: Refresh or reload data
- **Success (Green)**: Positive actions (Loan, Return)
- **Danger (Red)**: Delete or risky actions
- **Info (Blue)**: View or navigate actions

## Features & UX

### Responsive Design

- Works on desktop, tablet, and mobile devices
- Adaptive grid layout
- Touch-friendly buttons and forms

### Real-time Updates

- Refresh buttons to reload latest data
- Auto-refresh after actions
- Live search filtering

### Visual Feedback

- Success/error messages for all operations
- Status badges for availability
- Hover effects on interactive elements
- Loading states for data fetching

### Data Validation

- Required field validation on forms
- Book availability checks before loan creation
- Confirmation dialogs for destructive actions

## Error Handling

The GUI handles common errors gracefully:

- **Network Errors**: Displays error messages
- **Validation Errors**: Form validation on submit
- **API Errors**: User-friendly error messages
- **Empty States**: Helpful messages when no data exists

## Browser Compatibility

- Chrome/Edge 90+
- Firefox 88+
- Safari 14+
- Mobile browsers (iOS Safari, Chrome Mobile)

## Tips & Tricks

1. **Quick Loan**: From Books tab, click "Loan Book" to quickly borrow a book
2. **Bulk Status Check**: Use the search box to quickly find specific books
3. **Author Discovery**: Click Authors tab to see all works by a particular author
4. **Real-time Updates**: Refresh buttons ensure you see the latest data
5. **Non-destructive**: All actions can be undone (return a loan, change availability)

## Development

### File Structure

```
src/main/resources/static/
├── index.html      # Main HTML structure
├── styles.css      # All styling and responsive design
└── app.js         # JavaScript API interactions
```

### Technologies

- **HTML5**: Semantic markup
- **CSS3**: Grid, Flexbox, Gradients
- **JavaScript (ES6+)**: Async/await, Fetch API
- **Responsive Design**: Mobile-first approach

## Troubleshooting

### "Failed to add book"

- Check that all form fields are filled
- Verify the server is running
- Check browser console for error details

### Books won't loan

- Ensure the book has "Available" status
- Check that you selected a book from the dropdown
- Verify the API is responding correctly

### Search not working

- Clear the search box to reset
- Try searching with fewer characters
- Refresh the page and try again

### Empty authors list

- Create at least one book first (authors are auto-created)
- Refresh the Authors tab

## Performance Notes

- Initial load: ~500ms (includes data fetch and render)
- Search filtering: Instant (client-side)
- Book creation: ~1-2 seconds (server processing)
- Loan operations: ~1-2 seconds (server processing)

## Future Enhancements

- Book cover images
- User authentication
- Loan history
- Book ratings and reviews
- Advanced search filters
- Book availability calendar
- Export functionality
