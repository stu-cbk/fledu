import { $t } from "@/plugins/i18n";
import { english} from "@/router/enums";

export default {
  path: "/english",
  redirect: "/english/index",
  meta: {
    icon: "ri:english-input",
    title: $t("menus.pureChat"),
    rank: english,
    roles: ["student","admin1"]
  },
  children: [
    {
      path: "/english/index",
      name: "english",
      component: () => import("@/views/english/index.vue"),
      meta: {
        title: $t("menus.english"),
        roles: ["student","admin1"]
      }
    },
  ]
} satisfies RouteConfigsTable;