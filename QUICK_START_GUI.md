# 🚀 Quick Start Guide - GUI

## 30-Second Setup

1. **Start the server**:

   ```bash
   mvn spring-boot:run
   ```

2. **Open the GUI**:

   ```
   http://localhost:8080
   ```

3. **Done!** You're ready to manage books and loans.

---

## Common Tasks (Step-by-Step)

### Add a Book

1. Click **Books** tab
2. Fill in: Title, Author, ISBN, Year
3. Set "Available" to "Yes"
4. Click **Add Book**

### Loan a Book

1. Click **Books** tab
2. Find your book
3. Click **Loan Book** button
4. Confirm on Loans tab

### Return a Book

1. Click **Loans** tab
2. Find the loan
3. Click **Return Book**

### Find an Author

1. Click **Authors** tab
2. Scroll to find the author
3. See all their books

---

## Features at a Glance

| Feature             | Location    | How                        |
| ------------------- | ----------- | -------------------------- |
| Add Books           | Books tab   | Fill form + Click Add      |
| Search Books        | Books tab   | Type in search box         |
| View All Books      | Books tab   | Auto-loads on tab click    |
| Manage Availability | Books tab   | Mark Available/Unavailable |
| Create Loans        | Loans tab   | Select book + Click Create |
| Return Books        | Loans tab   | Click Return Book          |
| View Authors        | Authors tab | Auto-loads on tab click    |
| Refresh Data        | Any tab     | Click 🔄 Refresh button    |

---

## UI Colors & Meanings

- 🟢 **Green** = Available / Success
- 🔴 **Red** = Unavailable / Error
- 🔵 **Blue** = Info / Navigation
- ⚫ **Gray** = Neutral / Refresh

---

## Tips

✅ **Search in real-time** - Type to filter books instantly
✅ **Books auto-populate** - Author field creates new authors
✅ **Only available books** show in loan dropdown
✅ **Status updates instantly** when you loan/return books
✅ **All actions are safe** - Nothing is permanently deleted

---

## If Something Goes Wrong

| Problem          | Solution                                 |
| ---------------- | ---------------------------------------- |
| GUI won't load   | Check `http://localhost:8080` and reload |
| Can't add book   | Ensure all fields are filled             |
| Can't loan book  | Make sure book status is "Available"     |
| Data looks stale | Click the 🔄 Refresh button              |
| API errors       | Check server console for details         |

---

## Project Files

- **GUI**: `src/main/resources/static/`
- **API**: `src/main/java/com/example/api/demo/`
- **Tests**: `src/test/java/com/example/api/demo/`

---

## More Information

- Full GUI documentation: See `GUI_USAGE.md`
- API documentation: Check Swagger at `/swagger-ui.html` (if enabled)
- Code structure: See project `README.md`

---

**Happy reading! 📚**
