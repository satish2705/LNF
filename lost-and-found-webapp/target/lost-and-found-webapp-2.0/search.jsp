<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Items</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <header>
        <h1>Lost & Found</h1>
        <nav>
            <ul>
                <li><a href="index.jsp">Home</a></li>
                <li><a href="post.jsp">Post an Item</a></li>
                <li><a href="search.jsp" class="active">Search Items</a></li>
            </ul>
        </nav>
    </header>

    <main>
        <h2>Search Items</h2>
        <form onsubmit="searchItems(event)">
            <input type="text" id="keyword" placeholder="Enter keyword..." required>
            <select id="type">
                <option value="lost">Lost</option>
                <option value="found">Found</option>
            </select>
            <label for="startDate">Start Date:</label>
            <input type="date" id="startDate">
            <label for="endDate">End Date:</label>
            <input type="date" id="endDate">
            <button type="submit">Search</button>
        </form>

        <div id="results"></div>

        <!-- Pagination Controls -->
        <div id="pagination">
            <button id="prevPage" onclick="changePage(-1)">Previous</button>
            <span id="pageInfo"></span>
            <button id="nextPage" onclick="changePage(1)">Next</button>
        </div>
    </main>

    <footer>
        <p>&copy; 2025 Lost & Found. All rights reserved.</p>
    </footer>

    <script>
        let currentPage = 1;
        const resultsPerPage = 5;
        let currentData = [];

        function searchItems(event) {
            event.preventDefault();
            const keyword = document.getElementById("keyword").value.trim();
            const type = document.getElementById("type").value;
            const startDate = document.getElementById("startDate").value;
            const endDate = document.getElementById("endDate").value;

            const resultsDiv = document.getElementById("results");
            resultsDiv.innerHTML = "<p>Loading...</p>";

            fetch("/searchItems?keyword=${encodeURIComponent(keyword)}&type=${encodeURIComponent(type)}&startDate=${encodeURIComponent(startDate)}&endDate=${encodeURIComponent(endDate)}&page=${currentPage}&resultsPerPage=${resultsPerPage}")
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    currentData = data;
                    displayResults(data);
                })
                .catch(error => {
                    console.error("Error fetching search results:", error);
                    resultsDiv.innerHTML = "<p>An error occurred while fetching the search results. Please try again later.</p>";
                });
        }

        function displayResults(data) {
            const resultsDiv = document.getElementById("results");
            resultsDiv.innerHTML = "";

            if (data.length === 0) {
                resultsDiv.innerHTML = "<p>No items found.</p>";
                return;
            }

            data.forEach(item => {
                const itemDiv = document.createElement("div");
                itemDiv.innerHTML = `
                    <h3>${item.item_name}</h3>
                    <p>${item.description}</p>
                    <img src="${item.image_path}" alt="${item.item_name}" style="max-width: 100%; height: auto; border-radius: 5px;">
                `;
                resultsDiv.appendChild(itemDiv);
            });

            document.getElementById("pageInfo").textContent = `Page ${currentPage}`;
        }

        function changePage(direction) {
            currentPage += direction;
            if (currentPage < 1) {
                currentPage = 1;
            }
            searchItems(new Event("submit"));
        }
    </script>
</body>
</html>