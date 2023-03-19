<template>
  <!-- 
使用说明：
1）、引入category-cascader.vue
2）、语法：<category-cascader :catelogPath.sync="catelogPath"></category-cascader>
    解释：
      catelogPath：指定的值是cascader初始化需要显示的值，应该和父组件的catelogPath绑定;
          由于有sync修饰符，所以cascader路径变化以后自动会修改父的catelogPath，这是结合子组件this.$emit("update:catelogPath",v);做的
      -->
  <div>
    <el-select
      v-model="categoryId"
      @change="changeCatId"
      filterable
      placeholder="请选择"
    >
      <el-option
        v-for="item in categorys"
        :key="item.catId"
        :label="item.name"
        :value="item.catId"
      >
      </el-option>
    </el-select>
  </div>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';

export default {
  //import引入的组件需要注入到对象中才能使用
  components: {},
  //接受父组件传来的值
  props: {
    categoryId: {
      type: Number,
      default: null,
    },
  },
  data() {
    //这里存放数据
    return {
      setting: {
        value: "catId",
        label: "name",
      },
      categorys: [],
      category: this.categoryId,
    };
  },
  //方法集合
  methods: {
    getCategorys() {
      this.$http({
        url: this.$http.adornUrl("/product/category/list/all"),
        method: "get",
      }).then(({ data }) => {
        this.categorys = data.data;
      });
    },
    changeCatId(val) {
      debugger;
      this.$emit("changeCatId", val);
    },
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    this.getCategorys();
  },
};
</script>
<style scoped>
</style>