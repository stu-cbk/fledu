import { $t } from "@/plugins/i18n";
import { role } from "@/router/enums";

export default {
  path: "/role",
  redirect: "/role/index",
  meta: {
    icon: "ri:wechat-2-fill",
    title: $t("menus.role"),
    rank: role,
    roles: ["admin1"]
  },
  children: [
    {
      path: "/role/index",
      name: "role",
      component: () => import("@/views/role/index.vue"),
      meta: {
        title: $t("menus.role"),
        roles: ["admin1"]
      }
    }
  ]
} satisfies RouteConfigsTable;
