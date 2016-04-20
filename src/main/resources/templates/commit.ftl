<div class="row" style="padding-bottom: 20px">
    <div class="col-md-10">
        <div class="input-group">
            <span class="input-group-addon" id="basic-addon3">Commit message: </span>
            <input type="text" class="form-control" id="basic-url" aria-describedby="basic-addon3" name="commit-input">
        </div>
    </div>
    <div class="col-md-2"><button type="submit" class="btn btn-default" id="commit-button">Go</button></div>
</div>


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