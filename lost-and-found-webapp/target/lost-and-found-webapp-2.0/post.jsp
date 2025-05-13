<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Post Lost/Found Item</title>
  <link rel="stylesheet" href="styles.css">
</head>
<body>
  <header>
    <h1>Lost & Found</h1>
    <nav>
      <ul>
        <li><a href="index.jsp">Home</a></li>
        <li><a href="post.jsp" class="active">Post an Item</a></li>
        <li><a href="search.jsp">Search Items</a></li>
        <li><a href="contact.jsp">Contact Us</a></li>
      </ul>
    </nav>
  </header>

  <main>
    <section>
      <h2>Post Lost/Found Item</h2>
      <form id="postItemForm" action="submitItem" method="post" enctype="multipart/form-data">
        <label for="itemName">Item Name:</label>
        <input type="text" id="itemName" name="itemName" required>

        <label for="description">Description:</label>
        <textarea id="description" name="description" required></textarea>

        <label for="status">Status:</label>
        <select id="status" name="status" required>
          <option value="lost">Lost</option>
          <option value="found">Found</option>
        </select>

        <label for="contact">Contact Information:</label>
        <input type="text" id="contact" name="contact" placeholder="Enter your email or phone number" required>

        <label for="image">Upload Image:</label>
        <input type="file" id="image" name="image" accept="image/*" required>

        <button type="submit">Submit</button>
      </form>
    </section>
  </main>

  <footer>
    <p>&copy; 2025 Lost & Found. All rights reserved.</p>
  </footer>
</body>
</html>