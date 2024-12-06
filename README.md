# API 명세서


### 1. 일정 등록
- Method: POST
- URL: api/schedules

Request
```
{
    "authorId": 1,
    "email": "test@test.com",
    "title": "오늘 할일",
    "password": "12345678",
    "contents": "해야될 일"
}
```

Response
```
{
    "id": 1,
    "authorId": 1,
    "userName": "진짜 이름",
    "title": "오늘 할일",
    "password": "12345678",
    "contents": "해야될 일",
    "createDate": "2024-12-06T12:30:20.26",
    "updateDate": "2024-12-06T12:30:20.26"
}
```


### 2. 일정 조회
- Method: GET
- URL: api/schedules/{id}

Response
```
{
    "id": 1,
    "authorId": 1,
    "userName": "진짜 이름",
    "title": "제목1",
    "password": "12345678",
    "contents": "",
    "createDate": "2024-12-03T19:07:13",
    "updateDate": "2024-12-06T11:48:55"
}
```


### 3. 일정 목록조회
- Method: GET
- URL: api/schedules

Response
```
[
    {
        "id": 1,
        "authorId": 1,
        "userName": "진짜 이름",
        "title": "제목1",
        "password": "1234",
        "contents": "",
        "createDate": "2024-12-03T19:07:13",
        "updateDate": "2024-12-06T11:48:55"
    },
    {
        "id": 2,
        "authorId": 2,
        "userName": "작성자2",
        "title": "제목2",
        "password": "1234",
        "contents": "내용2",
        "createDate": "2024-12-03T19:08:02",
        "updateDate": "2024-12-04T16:13:56"
    }
]
```


### 4. 일정 수정
- Method: PATCH
- URL: api/schedules/{id}

Request
```
{
    "password": "12345678",
    "userName": "수정된 작성자명",
    "contents": "수정된 일정"
}
```

Response
```
{
    "id": 1,
    "authorId": 1,
    "userName": "수정된 작성자명",
    "title": "제목1",
    "password": "12345678",
    "contents": "수정된 일정",
    "createDate": "2024-12-03T19:07:13",
    "updateDate": "2024-12-06T12:34:15"
}
```


### 4. 일정 삭제
- Method: DELETE
- URL: api/schedules/{id}

Request
```
{
    "password": "12345678"
}
```

Response
```
{
    "message": "삭제가 완료되었습니다."
}
```

#### POSTMAN LINK
https://documenter.getpostman.com/view/40090331/2sAYBa88jG


# ERD
<img width="443" alt="erd" src="https://github.com/user-attachments/assets/725dd144-a82e-4b83-bcdd-c8d3863881b7">


# API 상세설명

### 일정 등록
작성자가 이미 로그인되어있다는 가정하에 작성자의 고유 식별 ID와 함께 요청 객체를 전달합니다.
작성자 데이터가 있다면 일정을 정상 등록합니다.


### 일정 조회
작성자의 ID값으로 일정을 조회합니다.


### 일정 목록 조회
날짜, 작성자 ID 값을 받아 일정 목록을 조회합니다.
size와 page 값을 받아 데이터를 나누어 조회합니다.


### 일정 수정
비밀번호가 일치하는 지 확인하고 작성자명과 일정을 수정합니다.


### 일정 삭제
비밀번호가 일치하는 지 확인하고 일정을 삭제합니다.
