import sys
import requests

def send_notification(log_file, status):
    with open(log_file, 'r') as file:
        log_content = file.read()

    if status == "failure":
        message = f"Build failed with the following log:\n{log_content}"
    else:
        message = f"Build succeeded! Message:\n{log_content}"

    # 여기에서 알림을 전송하는 로직을 추가하세요
    token = "7434834048:AAFkQxwLQ36rTdvmE4lDDMAssszQ2kzQgS0"
    chat_id = "6303810683"
    url = f"https://api.telegram.org/bot{token}/sendMessage"
    params = {
        "chat_id": chat_id,
        "text": message
    }
    result = requests.get(url, params=params)

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python notification_script.py <log_file> <status>")
        sys.exit(1)

    log_file = sys.argv[1]
    status = sys.argv[2]
    send_notification(log_file, status)