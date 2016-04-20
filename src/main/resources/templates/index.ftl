<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<div class="page-header">
    <h1>GIT repo managing <small>Get, modify scenarios, commit, and push to remote repo</small></h1>
</div>

<#if errorMessage??>
    <div class="alert alert-danger" role="alert">${errorMessage}</div>
</#if>

<form method="POST" action="/login">
    <div class="form-group">
        <label for="for-repository-url">Repository URL</label>
        <input type="text" class="form-control" id="repository-url" name="repository-url" placeholder=""/>
        <label for="for-email-address">Email address</label>
        <input type="text" class="form-control" id="email-address" name="email-address" placeholder=""/>
    </div>
    <div class="form-group">
        <label for="for-password">Password</label>
        <input type="password" class="form-control" id="password" name="password" placeholder=""/>
    </div>

    <button type="submit" class="btn btn-default" >Submit</button>
</form>





<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
</body>
</html>