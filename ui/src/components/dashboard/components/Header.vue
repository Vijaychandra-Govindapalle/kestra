<template>
    <TopNavBar :title="routeInfo.title" :not-starrable="notStarrable">
        <template #additional-right v-if="canCreate">
            <ul>
                <li>
                    <router-link
                        :to="{name: 'flows/create'}"
                        data-test-id="dashboard-create-button"
                    >
                        <el-button :icon="Plus" type="primary">
                            {{ $t("create_flow") }}
                        </el-button>
                    </router-link>
                </li>
            </ul>
        </template>
    </TopNavBar>
</template>

<script setup>
    import {computed} from "vue";

    import {useStore} from "vuex";
    import {useI18n} from "vue-i18n";

    import permission from "../../../models/permission";
    import action from "../../../models/action";

    import TopNavBar from "../../layout/TopNavBar.vue";

    import Plus from "vue-material-design-icons/Plus.vue";

    defineProps({
        notStarrable: Boolean,
    });

    const store = useStore();
    const {t} = useI18n({useScope: "global"});

    const user = computed(() => store.state.auth.user);
    const canCreate = computed(() =>
        user.value.isAllowedGlobal(permission.FLOW, action.CREATE),
    );

    const routeInfo = computed(() => ({title: t("homeDashboard.title")}));
</script>
