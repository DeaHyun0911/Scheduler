# API 명세서


### 1. 일정 등록
- Method: POST
- URL: api/schedules

Request
```
{
  "user_name": "작성자1",
  "password": "1234",
  "title": "제목1",
  "contents": "작성된 내용1"
}
```

Response
```
{
    "schedule_id": "1",
    "user_id": "1",
    "user_name": "작성자1",
    "title": "제목1",
    "contents": "내용1",
    "create_date": "생성된 날짜",
    "edit_date": "수정된 날짜"
}
```


### 2. 일정 조회
- Method: GET
- URL: api/schedules/{id}

Response
```
{
    "schedule_id": {Id},
    "user_id": "1",
    "user_name": "작성자1",
    "title": "제목1",
    "contents": "내용1",
    "create_date": "생성된 날짜",
    "edit_date": "수정된 날짜"
}
```


### 3. 일정 목록조회
- Method: GET
- URL: api/schedules

Response
```
[
{
    "schedule_id": "1",
    "user_id": "1",
    "user_name": "작성자1",
    "title": "제목1",
    "contents": "내용1",
    "create_date": "생성된 날짜",
    "edit_date": "수정된 날짜"
},
{
    "schedule_id": "2",
    "user_id": "1",
    "user_name": "작성자1",
    "title": "제목2",
    "contents": "내용2",
    "create_date": "생성된 날짜",
    "edit_date": "수정된 날짜"
}
]
```


### 4. 일정 수정
- Method: PATCH
- URL: api/schedules/{id}

Request
```
{
    "password": "1234",
    "title": "수정된 제목",
    "contents": "수정된 내용"
}
```

Response
```
{
    "schedule_id": "1",
    "user_id": "1",
    "user_name": "작성자1",
    "title": "제목1",
    "contents": "내용1",
    "create_date": "생성된 날짜",
    "edit_date": "수정된 날짜"
}
```


### 4. 일정 삭제
- Method: DELETE
- URL: api/schedules/{id}

Response
```
{
    "message": "삭제가 완료되었습니다."
}
```

#### POSTMAN LINK
https://documenter.getpostman.com/view/40090331/2sAYBa88jG
