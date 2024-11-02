document.getElementById("startCrawling").addEventListener("click", function () {
    fetch('/api/start-crawling', {  // Spring Boot의 API 경로에 맞게 수정 (HTTPS를 통해 Nginx로 프록시)
        method: 'POST'
    })
        .then(response => {
            if (response.status === 200) {
                return response.text();  // 정상 처리 시 응답 본문을 반환
            } else {
                throw new Error("오류 발생: " + response.status);  // 상태 코드가 200이 아닌 경우 오류 발생
            }
        })
        .then(() => {
            alert("크롤링이 시작되었습니다!");
        })
        .catch(error => {
            alert("오류가 발생했습니다: " + error.message);  // 오류 처리
            console.error("Error:", error);
        });
});

// SSH 아이콘에 클릭 이벤트 리스너 추가
document.getElementById("sshIcon").addEventListener("click", function () {
    fetch('/api/start-ssh', {  // 요청 URL을 /api/start-ssh로 설정
        method: 'POST'
    })
        .then(response => {
            if (response.status === 200) {
                return response.text();  // 정상 처리 시 응답 본문을 반환
            } else {
                throw new Error("오류 발생: " + response.status);  // 상태 코드가 200이 아닌 경우 오류 발생
            }
        })
        .then(() => {
            alert("SSH 접속 가능상태로 변경되었습니다!\n접속허용시간 1분");
        })
        .catch(error => {
            alert("오류가 발생했습니다: " + error.message);  // 오류 처리
            console.error("Error:", error);
        });
});
