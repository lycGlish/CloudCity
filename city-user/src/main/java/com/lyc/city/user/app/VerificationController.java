package com.lyc.city.user.app;

import com.baomidou.kaptcha.Kaptcha;
import com.baomidou.kaptcha.exception.KaptchaIncorrectException;
import com.baomidou.kaptcha.exception.KaptchaNotFoundException;
import com.baomidou.kaptcha.exception.KaptchaRenderException;
import com.baomidou.kaptcha.exception.KaptchaTimeoutException;
import com.lyc.city.constant.RestConstant;
import com.lyc.city.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lyc
 * @date 2020/11/23 14:38
 */
@RestController
public class VerificationController {

    @Autowired
    private Kaptcha kaptcha;

    @GetMapping("/render")
    public void render() {
        kaptcha.render();
    }

    @PostMapping("/valid")
    public R valid(@RequestParam("verificationCode") String verificationCode) {
        try {
            kaptcha.validate(verificationCode,60);
        } catch (KaptchaIncorrectException e) {
            return R.error(RestConstant.KaptchaErrorCode,"KaptchaIncorrectException");
        } catch (KaptchaNotFoundException e) {
            return R.error(RestConstant.KaptchaErrorCode,"KaptchaNotFoundException");
        } catch (KaptchaRenderException e) {
            return R.error(RestConstant.KaptchaErrorCode,"KaptchaRenderException");
        } catch (KaptchaTimeoutException e) {
            return R.error(RestConstant.KaptchaErrorCode,"KaptchaTimeoutException");
        }
        return R.ok();
    }
}
