<div style="width:80%">
    <div class="input-group">
        <span class="input-group-addon" id="basic-addon3">Search for automation by text</span>
        <input type="text" class="form-control" id="basic-url" aria-describedby="basic-addon3" name="commit-input">
    </div>
</div>
<button type="submit" class="btn btn-default" id="commit-button">Go</button>

<div class="alert alert-success hidden" role="alert" id="success-commit">Sucessfull commit</div>
<div class="alert alert-danger hidden" role="alert" id="failed-commit">Failed commit</div>

<script>
    $(document).ready(function() {
        $("#commit-button").click(function() {
            $.ajax("http://localhost:8080/addCommitPush?message="+encodeURIComponent($("#commit-input").val()), {
                type: 'GET',
                timeout:10000,
                success: function(data) {
                    if(data.statusCode === 200 || data.statusCode === 201) {
                        $("#success-commit").removeClass("hidden");
                        $("#failed-commit").addClass("hidden");
                    } else {
                        $("#failed-commit").removeClass("hidden");
                        $("#success-commit").addClass("hidden");
                    }
                },
                error: function() {
                    $("#failed-commit").removeClass("hidden");
                    $("#success-commit").addClass("hidden");
                }
            });
        });
    })
</script>