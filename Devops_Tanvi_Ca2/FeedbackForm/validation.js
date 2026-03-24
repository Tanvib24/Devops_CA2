document.getElementById("feedbackForm").addEventListener("submit", function(e) {

    e.preventDefault();

    let valid = true;

    function setError(id, msg) {
        document.getElementById(id).innerText = msg;
        if (msg !== "") valid = false;
    }

    // clear old state
    document.querySelectorAll(".error").forEach(el => el.innerText = "");
    document.getElementById("successMessage").style.display = "none";

    let name = document.getElementById("studentName").value.trim();
    let email = document.getElementById("email").value.trim();
    let mobile = document.getElementById("mobile").value.trim();
    let dept = document.getElementById("department").value;
    let feedback = document.getElementById("feedback").value.trim();
    let gender = document.querySelector('input[name="gender"]:checked');

    setError("nameError", name === "" ? "Name required" : "");

    setError("emailError",
        /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
        ? "" : "Invalid email");

    setError("mobileError",
        /^[0-9]{10}$/.test(mobile)
        ? "" : "Invalid mobile");

    setError("departmentError",
        dept === "" ? "Select department" : "");

    setError("genderError",
        gender ? "" : "Select gender");

    setError("feedbackError",
        feedback.split(/\s+/).length >= 10
        ? "" : "Minimum 10 words required");

    if(valid) {
        document.getElementById("successMessage").style.display = "block";
    }
});