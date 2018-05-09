const express = require('express');
const app = express();
let users = [
  {
    id: 1,
    name: 'alice'
  },
  {
    id: 2,
    name: 'bek'
  },
  {
    id: 3,
    name: 'chris'
  }
]

//단순히 http://localhost:3000/users로 접근하면 users라는 객체를 json으로 response하라는 의미입니다.
app.get('/users', (req, res) => {
   console.log('who get in here/users');
   res.json(users)
});

app.listen(7088, () => {
  console.log('Example app listening on port 7088!');
});


