<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>History</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<body>

<div class="page-header">
    <h1>The changes in the commit</h1>
    <div class="well">
        <a href="/getFiles" class="btn"><span class="glyphicon glyphicon-chevron-left"></span> Back</a>
    </div>
</div>

<#if errorMessage??>
    <div class="panel panel-danger">${errorMessage}</div>
</#if>
<#if history??>
    <#if history != "[]">
    <div class="panel panel-primary">${history}</div>
    </#if>
    <#if history == "[]">
    <div class="panel panel-primary">No changes in this commit! </div>
    </#if>
</#if>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
      integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
</body>
</html>
