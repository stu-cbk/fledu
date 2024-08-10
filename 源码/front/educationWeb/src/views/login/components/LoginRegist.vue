<script setup lang="ts">
import { useI18n } from "vue-i18n";
import { ref, reactive } from "vue";
import Motion from "../utils/motion";
import { message } from "@/utils/message";
import { updateRules } from "../utils/rule";
import type { FormInstance } from "element-plus";
import { useVerifyCode } from "../utils/verifyCode";
import { $t, transformI18n } from "@/plugins/i18n";
import { useUserStoreHook } from "@/store/modules/user";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";
import Lock from "@iconify-icons/ri/lock-fill";
import Iphone from "@iconify-icons/ep/iphone";
import User from "@iconify-icons/ri/user-3-fill";
import Mail from "@iconify-icons/ri/mail-fill"

const { t } = useI18n();
const checked = ref(false);
const loading = ref(false);
const ruleForm = reactive({
  username: "",
  phone: "",
  password: "",
  repeatPassword: "",
  type: "0", // Assuming this is where you store user type (0 for student, 1 for teacher, 2 for admin)
  userEmail: "" // New field for user email
});
const ruleFormRef = ref<FormInstance>();
const { isDisabled, text } = useVerifyCode();
const repeatPasswordRule = [
  {
    validator: (rule, value, callback) => {
      if (value === "") {
        callback(new Error(transformI18n($t("login.purePassWordSureReg"))));
      } else if (ruleForm.password !== value) {
        callback(
          new Error(transformI18n($t("login.purePassWordDifferentReg")))
        );
      } else {
        callback();
      }
    },
    trigger: "blur"
  }
];

const onUpdate = async (formEl: FormInstance | undefined) => {
  loading.value = true;
  if (!formEl) return;
  await formEl.validate(valid => {
    if (valid) {
      if (checked.value) {
        // 检查用户是否存在
        const existCheck = async () => {
          try {
            console.log('type', ruleForm.type)
            console.log('userName', ruleForm.username)
            const response = await fetch(`http://47.116.201.0:10010/user/exist?type=${ruleForm.type}&userName=${ruleForm.username}`, {
              method: "GET",
              headers: {
                "Content-Type": "application/json"
              },
            });
            const data = await response.json();
            if (data.exist) {
              message(transformI18n(t("login.pureUserExist")), {
                type: "warning"
              });
              loading.value = false;
            } else {
              // 注册用户
              await registerUser();
              message("注册成功", {
                type: "info"
              });
            }
          } catch (error) {
            console.error("Error checking user existence:", error);
            loading.value = false;
          }
        };

        // 注册用户
        const registerUser = async () => {
          try {
            const response = await fetch("http://47.116.201.0:10010/user/register", {
              method: "POST",
              headers: {
                "Content-Type": "application/json"
              },
              body: JSON.stringify({
                type: ruleForm.type,
                userEmail: ruleForm.userEmail,
                userName: ruleForm.username,
                userPassword: ruleForm.password,
                userPhone: ruleForm.phone
              })
            });
            const data = await response.json();
            console.log(data)
            if (data.success) {
              message(transformI18n(t("login.pureRegisterSuccess")), {
                type: "success"
              });
            } else {
              message(transformI18n(t("login.pureRegisterFail")), {
                type: "error"
              });
            }
            loading.value = false;
          } catch (error) {
            console.error("Error registering user:", error);
            loading.value = false;
          }
        };

        // 执行检查和注册操作
        existCheck();
      } else {
        loading.value = false;
        message(transformI18n($t("login.pureTickPrivacy")), {
          type: "warning"
        });
      }
    } else {
      loading.value = false;
    }
  });
};

const identity = ref('student'); // 默认选中老师角色

const setIdentity = (role) => {
  identity.value = role;
  switch (role) {
    case 'teacher':
      ruleForm.type = "1";
      break;
    case 'student':
      ruleForm.type = "0";
      break;
    case 'admin':
      ruleForm.type = "2";
      break;
    default:
      ruleForm.type = "0";
      break;
  }
};

function onBack() {
  useVerifyCode().end();
  useUserStoreHook().SET_CURRENTPAGE(0);
}
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="ruleForm"
    :rules="updateRules"
    size="large"
  >
     <Motion>
      <!-- 身份选项 -->
      <el-form-item>
        <div class="identity-buttons">
          <el-radio-group v-model="identity">
            <el-radio-button :class="{ 'active': identity === 'teacher' }" label="teacher" @click="setIdentity('teacher')">
              {{ t("login.teacher") }}
            </el-radio-button>
            <el-radio-button :class="{ 'active': identity === 'student' }" label="student" @click="setIdentity('student')">
              {{ t("login.student") }}
            </el-radio-button>
            <el-radio-button :class="{ 'active': identity === 'admin' }" label="admin" @click="setIdentity('admin')">
              {{ t("login.admin") }}
            </el-radio-button>
          </el-radio-group>
        </div>
      </el-form-item>
    </Motion>

    <Motion>
      <el-form-item
        :rules="[
          {
            required: true,
            message: transformI18n($t('login.pureUsernameReg')),
            trigger: 'blur'
          }
        ]"
        prop="username"
      >
        <el-input
          v-model="ruleForm.username"
          clearable
          :placeholder="t('login.pureUsername')"
          :prefix-icon="useRenderIcon(User)"
        />
      </el-form-item>
    </Motion>

    

    <Motion :delay="150">
      <el-form-item prop="userEmail">
        <el-input
          v-model="ruleForm.userEmail"
          clearable
          :placeholder="t('login.pureEmail')"
          :prefix-icon="useRenderIcon(Mail)"
        />
      </el-form-item>
    </Motion>
    <Motion :delay="200">
      <el-form-item prop="password">
        <el-input
          v-model="ruleForm.password"
          clearable
          show-password
          :placeholder="t('login.purePassword')"
          :prefix-icon="useRenderIcon(Lock)"
        />
      </el-form-item>
    </Motion>

    <Motion :delay="250">
      <el-form-item :rules="repeatPasswordRule" prop="repeatPassword">
        <el-input
          v-model="ruleForm.repeatPassword"
          clearable
          show-password
          :placeholder="t('login.pureSure')"
          :prefix-icon="useRenderIcon(Lock)"
        />
      </el-form-item>
    </Motion>

    <Motion :delay="100">
      <el-form-item prop="phone">
        <el-input
          v-model="ruleForm.phone"
          clearable
          :placeholder="t('login.purePhone')"
          :prefix-icon="useRenderIcon(Iphone)"
        />
      </el-form-item>
    </Motion>

    <Motion :delay="350">
      <el-form-item>
        <el-button
          class="w-full"
          size="default"
          type="primary"
          :loading="loading"
          @click="onUpdate(ruleFormRef)"
        >
          {{ t("login.pureDefinite") }}
        </el-button>
      </el-form-item>
    </Motion>

    <Motion :delay="400">
      <el-form-item>
        <el-button class="w-full" size="default" @click="onBack">
          {{ t("login.pureBack") }}
        </el-button>
      </el-form-item>
    </Motion>
  </el-form>
</template>

<style scoped>
.phone-verification {
  display: flex;
  align-items: center;
}

.verify-code-input {
  flex: 1; /* Takes remaining space */
  margin-right: 10px; /* Adjust margin for spacing between input and button */
}

.verify-code-button {
  min-width: 120px; /* Adjust width as necessary */
  background-color: #ccc; /* Gray background */
}
</style>