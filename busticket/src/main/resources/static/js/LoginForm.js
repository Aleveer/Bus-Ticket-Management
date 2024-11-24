// Function to get query parameters
function getQueryParam(param) {
    let urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}

// Display success message
let successMessage = getQueryParam('message');
if (successMessage) {
    alert(successMessage);
}

// Display error message
let errorMessage = getQueryParam('error');
if (errorMessage) {
    alert(errorMessage);
}
document.getElementById("loginForm").addEventListener("submit", async function (event) {
    event.preventDefault();
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ email, password }),
        });

        if (!response.ok) {
            throw new Error(`Network response was not ok: ${response.statusText}`);
        }

        const data = await response.json();

        if (data.success) {
            alert("Login successful");
            //localStorage.setItem("jwtToken", data.data.loginToken);
            window.location.href = "/home/index"; // Redirect to index.html
        } else {
            alert(`Failed to login: ${data.message}`);
        }
    } catch (error) {
        console.error("Error:", error);
        alert(`Error: ${error.message}`);
    }
});