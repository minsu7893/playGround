document.getElementById("startCrawling").addEventListener("click", function () {
    fetch('/api/start-crawling', {  // Spring Boot의 API 경로에 맞게 수정 (HTTPS를 통해 Nginx로 프록시)
        method: 'POST'
    })
    .then(response => response.text())
    .then(data => {
        alert("크롤링이 시작되었습니다!");
    })
    .catch(error => {
        console.error("Error:", error);
    });
});