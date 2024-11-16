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


document.getElementById("startKtxMacro").addEventListener("click", function () {
    // 사용자가 입력한 값을 가져옵니다.
    const startStation = document.getElementById("startStation").value;
    const endStation = document.getElementById("endStation").value;
    const customDate = document.getElementById("customDate").value;
    const departureTime = document.getElementById("departureTime").value;

    // 날짜에서 연도, 월, 일 추출
    const date = new Date(customDate);
    const year = date.getFullYear(); // 숫자로 유지
    const month = date.getMonth() + 1; // 숫자로 유지, 월은 0부터 시작하므로 +1
    const day = date.getDate(); // 숫자로 유지

    // 요일 추출
    const dayOfWeekArr = ["일", "월", "화", "수", "목", "금", "토"];
    const dayOfWeek = dayOfWeekArr[date.getDay()];

    // KtxRequest 객체 생성
    const ktxRequest = {
        start: startStation,
        end: endStation,
        year: year,
        month: month,
        day: day,
        dayOfWeek: dayOfWeek,
        time: departureTime
    };

    // POST 요청 보내기
    fetch('/api/start-ktx', {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(ktxRequest)
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then((text) => {
                    throw new Error(data.prcsRslt);
                });
            }
            return response.json(); // JSON으로 응답을 파싱
        })
        .then(data => {
            console.log("서버 응답:", data);
            alert(data.prcsRslt); // prcsRslt 필드를 출력
        })
        .catch(error => {
            console.error("에러 발생:", error);
            alert(`에러 발생: ${error.message}`); // 에러 메시지 표시
        });

});

document.getElementById("stopKtxMacro").addEventListener("click", function () {

    // POST 요청 보내기
    fetch('/stop-ktx', {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then((text) => {
                    throw new Error(`오류 코드: ${response.status}, 메시지: ${text}`);
                });
            }
            return response.text();
        })
        .then(data => {
            console.log("서버 응답:", data);
            alert("KTX 매크로가 성공적으로 시작되었습니다.");
        })
        .catch(error => {
            console.error("에러 발생:", error);
            alert(`${error.message}`);
        });
});