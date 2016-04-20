<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${file.title}</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <script type="application/javascript">
        $(document).ready(function() {
            var $modal = $('#scenario-editor');
            var $scenarioTitle = $modal.find("#scenario-title");
            var $scenarioContent = $modal.find("#scenario-content");
            var scenario = {};
            $('button.scenario-edit').click(function(e) {
                e.preventDefault();
                $button = $(this);
                scenario = {
                    title: $button.data('scenario'),
                    content: $button.data('scenario-content')
                };
                $scenarioTitle.val(scenario.title);
                $scenarioContent.val(scenario.content);
            });
            $modal.find("#scenario-save").click(function() {
                newscenario = {
                    title: $scenarioTitle.val(),
                    content: $scenarioContent.val().split("\n")
                };
                $.ajax("http://localhost:8080/updateScenario?fileName=${file.fileName}&scenarioTitle=" + encodeURIComponent(scenario.title), {
                    data: JSON.stringify(newscenario),
                    contentType : 'application/json',
                    type: 'POST',
                    success: function(data) {
                        console.log(data);
                        $modal.find(".close").trigger("click");
                    }
                });
            });
        });
    </script>
</head>
<body>

<div class="page-header">
    <h1>Scenarios in '${file.title}'</h1>
</div>

<#include "commit.ftl">

<div class="well">
    <p>Filename: ${file.fileName}</p>
    <p>
    <#list file.description as descriptionLine>
        ${descriptionLine}<br>
    </#list>
    </p>
    <a href="/getFiles" class="btn"><span class="glyphicon glyphicon-chevron-left"></span>Back</a>
</div>
<table class="table table-striped">
    <thead>
    <th>Scenario title</th>
    <th>Scenario content</th>
    <th></th>
    </thead>
    <tbody>
    <#list file.scenarios as scenario>
    <tr>
        <td class="title">
        ${scenario.title}
        </td>
        <td class="content">
            <#list scenario.content as contentLine>${contentLine}<br></#list>

        </td>
        <td>
            <button data-scenario="${scenario.title?html}"
                    data-scenario-content="${((scenario.content)?join("\n"))?html}"
                    data-toggle="modal" data-target="#scenario-editor"
                    class="btn btn-default scenario-edit"/>

            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></td>
    </tr>
    <#else>
    <tr>
        <td>No scenarios retrived</td>
    </tr>
    </#list>
    </tbody>
</table>
<div id="scenario-editor" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Edit scenario</h4>
            </div>
            <div class="modal-body">
                <label for="scenario-title">Title:</label>
                <input type="text" id="scenario-title" class="form-control">
                <label for="scenario-content">Content:</label>
                <textarea id="scenario-content" rows="10" class="form-control"></textarea>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> Cancel</button>
                <button type="button" class="btn btn-default" id="scenario-save"><span class="glyphicon glyphicon-floppy-save"></span> Save</button>
            </div>
        </div>

    </div>
</div>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
      integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
</body>
</html>
