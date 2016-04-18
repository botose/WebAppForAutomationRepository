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
                    <td><a href="getFile/${file.fileName}" class="btn btn-default"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a></td>
                </tr>
                <tr>
                    <td colspan="3"> Description:
                        <#list file.description as descriptionLine>
                            ${descriptionLine} \n
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