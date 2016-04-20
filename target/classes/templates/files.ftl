<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Feature files</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
</head>
<body>
    <div class="page-header">
        <h1>Files in the active repository</h1>
    </div>

    <form method="GET" action="/searchForScenario">
        <div class="row" style="padding-bottom: 20px">
            <div class="col-md-10">
                <div class="input-group">
                    <span class="input-group-addon" id="basic-addon3">Search for automation by text</span>
                    <input type="text" class="form-control" id="basic-url" aria-describedby="basic-addon3" name="title">
                </div>
            </div>
            <div class="col-md-2">
                <button type="submit" class="btn btn-default">Go</button>
            </div>
        </div>
    </form>

    <#include "commit.ftl">

    <table class="table table-striped">
        <thead>
            <th>Filename</th>
            <th>Title</th>
            <th></th>
        </thead>
        <tbody>
            <#list files as file>
                <tr>
                    <td>${file.fileName}</td>
                    <td>${file.title}</td>
                    <td><a href="getFile/${file.fileName}" class="btn btn-default"><span class="glyphicon glyphicon-floppy-open" aria-hidden="true"></span></a></td>
                </tr>
                <tr>
                    <td colspan="3"> Description:
                        <#list file.description as descriptionLine>
                            ${descriptionLine}<br>
                        </#list>
                    </td>
                </tr>
            <#else>
                <tr>
                    <td>No files retrived</td>
                </tr>
            </#list>
        </tbody>
    </table>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
</body>
</html>