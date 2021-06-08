<!---->

<template>
  <div style="width: 100%; margin-top: 4em;">
    <v-container>
      <v-row justify="center" row>
        <v-col ms="12" md="12" lg="11" xl="8">
          <v-row>
            <v-col>
              <div align="center">
                <v-btn @click="show_isAddProduct" :disabled="isAddProduct" color="black"
                       class="light-green--text text--accent-2">
                  <v-icon>
                    add
                  </v-icon>
                </v-btn>
                <v-btn @click="show_isProducts" :disabled="isProducts" color="black"
                       class="light-green--text text--accent-2">
                  {{ text_page.form_component.button.products }}
                </v-btn>
              </div>
            </v-col>
            <v-col>
              <div align="center">
                <v-btn @click="show_isAddCategory" :disabled="isAddCategory" color="black"
                       class="light-green--text text--accent-2">
                  <v-icon>
                    add
                  </v-icon>
                </v-btn>
                <v-btn @click="show_isCategories" :disabled="isCategories" color="black"
                       class="light-green--text text--accent-2">
                  {{ text_page.form_component.button.categories }}
                </v-btn>
              </div>
            </v-col>
            <v-col>
              <div align="center">
                <v-btn @click="show_isOrders" :disabled="isOrders" color="black"
                       class="light-green--text text--accent-2">
                  {{ text_page.form_component.button.orders }}
                </v-btn>
                <v-btn @click="show_isUsers" :disabled="isUsers" color="black"
                       class="light-green--text text--accent-2">
                  {{ text_page.form_component.button.users }}
                </v-btn>
              </div>
            </v-col>
          </v-row>
          <v-card
              style="box-shadow: 0 0 25px;background: rgba(0, 0, 0, 0.93);border-radius: 20px;"
              dark
              elevation="16"
              class="mx-auto"
          >
            <div style="padding-top: 3em; padding-bottom: 3em;">
              <div class="scroll">
                <div style="padding-right: 2em;padding-left: 2em">
                  <v-container>
                    <v-row>

                      <div v-if="isProducts" class="col-md-4" v-for="product in  productsWithFiltersAndPagination"
                           v-bind:key="product.name">
                        <ProductCard :product="product"/>
                      </div>

                      <div v-if="isAddProduct">
                        <AddProduct/>
                      </div>

                      <v-container v-if="isUsers">
                        <v-row justify="center" row>
                          <v-col>
                            <div align="center">
                              <v-list style="width: 80%">
                                <div v-for="user in $store.state.App.users" :key="user.id">
                                  <User :user="user"/>
                                </div>
                              </v-list>
                            </div>
                          </v-col>
                        </v-row>
                      </v-container>

                      <v-container v-if="isCategories">
                        <v-row justify="center" row>
                          <v-col>
                            <div align="center">
                              <v-list style="width: 80%">
                                <div v-for="category in $store.state.App.categories" :key="category.id">
                                  <Category :category="category"/>
                                </div>
                              </v-list>
                            </div>
                          </v-col>
                        </v-row>
                      </v-container>

                      <div v-if="isAddCategory">
                        <AddCategory/>
                      </div>
                      <v-container v-if="isOrders">
                        <v-row justify="center" row>
                          <v-col>
                            <div align="center">
                              <v-list style="width: 68%">
                                <div v-for="order in this.$store.state.App.userOrders" :key="order.id">
                                  <Order :order="order"/>
                                </div>
                              </v-list>
                            </div>
                          </v-col>
                        </v-row>
                      </v-container>
                    </v-row>
                  </v-container>
                </div>
              </div>
            </div>
          </v-card>
          <v-row v-if="isProducts" style="padding-top: 2em;">
            <v-col cols="7">
              <v-row justify="center">
                <v-select
                    style="max-width: 320px"
                    dark
                    v-model="filtersValue"
                    :items="filtersItems"
                    chips
                    v-bind:label=text_page.form_component.button.choose_status_for_ordering
                    multiple
                    outlined
                ></v-select>
                <v-select
                    style="max-width: 150px; margin-left: 1em"
                    dark
                    v-model="ordersValue"
                    :items="ordersItems"
                    chips
                    v-bind:label=text_page.form_component.button.sort_by
                    outlined
                ></v-select>
              </v-row>
            </v-col>

            <v-col style="padding: 0">
              <div align="center">
                   <span style="color: white; padding-right: 5px" class="text-h5">
                {{ text_page.page_info.pages }}:&nbsp {{ pages.length }}
              </span>
                <span style="color: white;" class="text-h5">
                |
              </span>
                <span style="color: white; padding-left: 5px" class="text-h5">
                {{ text_page.page_info.products }}:&nbsp {{ productsWithFilters.length }}
              </span>
              </div>
              <div align="center" style="margin-top: 1em">
                <v-btn dark style="color: white" :disabled="page === 1" @click="page--">
                  <v-icon>navigate_before</v-icon>
                </v-btn>
                <v-btn dark style="color: white">
                  <span class="light-green--text text--accent-2">
                   {{ page }}
                 </span>
                </v-btn>
                <v-btn dark style="color: white" @click="page++" :disabled="page >= pages.length">
                  <v-icon>navigate_next</v-icon>
                </v-btn>
              </div>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import ProductCard from "vuejs/admin/component/home/component/product/ProductCard.vue";
import AddProduct from "vuejs/admin/component/home/component/addproduct/addProduct.vue";
import User from "vuejs/admin/component/home/component/user/user.vue";
import Category from "vuejs/admin/component/home/component/category/category.vue";
import AddCategory from "vuejs/admin/component/home/component/addcategory/addCategory.vue";
import Order from "vuejs/admin/component/home/component/order/order.vue";

export default {
  components: {
    ProductCard,
    AddProduct,
    User,
    Category,
    AddCategory,
    Order
  },
  data() {
    return {
      filtersItems: ['ACTIVE', 'INACTIVE', 'BLOCKED'],
      filtersValue: ['ACTIVE', 'INACTIVE', 'BLOCKED'],

      ordersItems: ['PRICE ↑', 'PRICE ↓', 'DATE ↑', 'DATE ↓'],
      ordersValue: 'DATE ↓',

      text_page: text_page,

      isProducts: true,
      isAddProduct: false,
      isOrders: false,
      isUsers: false,
      isCategories: false,
      isAddCategory: false,

      oldSelectCategory: this.$store.state.App.selectCategory,

      oldPage: 1,
      page: 1,
      perPage: 12,
      pages: [],
    }
  },
  methods: {
    clear_allComponents() {
      this.isProducts = false
      this.isAddProduct = false
      this.isOrders = false
      this.isUsers = false
      this.isCategories = false
      this.isEditCategory = false
      this.isAddCategory = false
    },
    show_isProducts() {
      this.clear_allComponents()
      this.isProducts = true
    },
    show_isCategories() {
      this.clear_allComponents()
      this.isCategories = true
    },
    show_isAddCategory() {
      this.clear_allComponents()
      this.isAddCategory = true
    },
    show_isAddProduct() {
      this.clear_allComponents()
      this.isAddProduct = true
    },
    show_isOrders() {
      this.clear_allComponents()
      this.isOrders = true
    },
    show_isUsers() {
      this.clear_allComponents()
      this.isUsers = true
    }
  },
  computed: {
    productsWithFilters() {
      var array = [];

      var products = this.$store.state.App.products
      if (this.ordersValue === 'PRICE ↑') {
        products = products.sort((a, b) => (a.price > b.price) ? 1 : -1)
      } else if (this.ordersValue === 'PRICE ↓') {
        products = products.sort((a, b) => (a.price < b.price) ? 1 : -1)
      } else if (this.ordersValue === 'DATE ↑') {
        products = products.sort((a, b) => (a.id > b.id) ? 1 : -1)
      } else if (this.ordersValue === 'DATE ↓') {
        products = products.sort((a, b) => (a.id < b.id) ? 1 : -1)
      }

      for (var i = 0; i < this.filtersValue.length; i++) {
        if (this.filtersValue[i] === 'ACTIVE') {
          var arrayConcat = products.filter(function (product) {
            return product.status === 'ACTIVE';
          })
          array = array.concat(arrayConcat)

        } else if (this.filtersValue[i] === 'INACTIVE') {
          var arrayConcat = products.filter(function (product) {
            return product.status === 'INACTIVE';
          })
          array = array.concat(arrayConcat)
        } else if (this.filtersValue[i] === 'BLOCKED') {
          var arrayConcat = this.$store.state.App.products.filter(function (product) {
            return product.status === 'BLOCKED';
          })
          array = array.concat(arrayConcat)
        }
      }
      if (this.$store.state.App.search !== '') {
        array = array.filter(item => item.name.indexOf(this.$store.state.App.search) !== -1)
        this.page = 1
        this.oldPage = 1
      }

      return array
    },
    productsWithFiltersAndPagination() {
      var array = this.productsWithFilters;

      let numberOfPages = Math.ceil(array.length / this.perPage);
      this.pages = []
      for (let index = 1; index <= numberOfPages; index++) {
        this.pages.push(index);
      }

      if (this.page === this.oldPage + 1 || this.page === this.oldPage - 1) {
        this.oldPage = this.page
      } else if (this.$store.state.App.selectCategory !== this.oldSelectCategory) {
        this.oldSelectCategory = this.$store.state.App.selectCategory
        this.page = 1
        this.oldPage = 1
      } else if (this.oldSelectSort !== this.ordersValue) {
        this.oldSelectSort = this.ordersValue
        this.page = 1
        this.oldPage = 1
      }

      let page = this.page;
      let perPage = this.perPage;
      let from = (page * perPage) - perPage;
      let to = (page * perPage);

      return array.slice(from, to);
    }
  }
}
</script>

<style scoped>

@media screen and (min-height: 1100px) {
  .scroll {
    margin: 0;
    padding: 0;
    width: 100%;
    height: 68vh;
    overflow-x: hidden;
    overflow-y: auto;
  }
}

@media screen and (max-height: 1100px) {
  .scroll {
    margin: 0;
    padding: 0;
    width: 100%;
    height: 60vh;
    overflow-x: hidden;
    overflow-y: auto;
  }
}
</style>