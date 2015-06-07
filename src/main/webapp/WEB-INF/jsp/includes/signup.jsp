<div class="modal fade" id="signupModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4><span class="glyphicon glyphicon-lock"></span><fmt:message key="signup.heading" /></h4>
        </div>
        <div class="modal-body" >
          <form method="post" action="${ctx}/signup" role="form"  accept-charset="UTF-8">
            <div class="form-group">
              <label for="usrname"><span class="glyphicon glyphicon-user"></span><fmt:message key="label.username" /></label>
              <input class="form-control" type="text" placeholder="Username" id="username" name="username" required>
            </div>
            <div class="form-group">
              <label for="password"><span class="glyphicon glyphicon-eye-open"></span><fmt:message key="label.password" /></label>
              <input class="form-control" type="password" placeholder="Password" id="password" name="password" required>
            </div>
            <div class="form-group">
              <label for="confirmPassword"><span class="glyphicon glyphicon-eye-open"></span>Confirm Password</label>
              <input class="form-control" type="password" placeholder="Confirm Password" id="confirmPassword" name="confirmPassword" required>
            </div>
            <div class="form-group">
              <label for="passwordHint"><span class="glyphicon glyphicon-tag">Password Hint</span></label>
              <input class="form-control" type="text" placeholder="Password Hint" id="passwordHint" name="passwordHint" required>
            </div>
            <div class="form-group">
                <img src="${ctx}/kaptcha.jpg?signup" id="signup_img" onclick="document.getElementById('signup_img').src = '${ctx}/kaptcha.jpg?signup=' + Math.random(); 
                       return false" class="img-thumbnail" alt="click click image to refresh if u can't recognize the content" /> 
                <input type="text" name="captcha" class="form-control" placeholder="Captcha" name="captcha" required>
                <a href="#" onclick="">Refresh Captcha</a>
            </div>
            <div class="form-group">
              <button type="submit" class="btn btn-success btn-block"><span class="glyphicon glyphicon-off"></span><fmt:message key="button.register" /></button>
              
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button type="submit" class="btn btn-danger btn-default pull-left" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> <fmt:message key="button.cancel" /></button>
          <p><a href="#">Sign In with QQ</a></p>
          <p><a href="#">Sign In with WeChat</a></p>
          <p><fmt:message key="login.passwordHint" /></p>
        </div>
      </div>
      
    </div>
  </div> 