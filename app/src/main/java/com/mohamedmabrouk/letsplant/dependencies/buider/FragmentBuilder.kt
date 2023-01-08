package com.mohamedmabrouk.letsplant.dependencies.buider

import com.mohamedmabrouk.letsplant.ui.discover.DiscoverFragment
import com.mohamedmabrouk.letsplant.ui.discover.articles.ArticlesFragment
import com.mohamedmabrouk.letsplant.ui.discover.articles.articleDetails.ArticleDetailsFragment
import com.mohamedmabrouk.letsplant.ui.discover.plants.PlantsListFragment
import com.mohamedmabrouk.letsplant.ui.discover.plants.plantDetails.PlantDetailsFragment
import com.mohamedmabrouk.letsplant.ui.myPlants.MyPlantsFragment
import com.mohamedmabrouk.letsplant.ui.reminders.RemindersFragment
import com.mohamedmabrouk.letsplant.ui.reminders.RemindersListFragment
import com.mohamedmabrouk.letsplant.ui.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {
    @ContributesAndroidInjector
    abstract fun bindsArticleDetailsFragment(): ArticleDetailsFragment

    @ContributesAndroidInjector
    abstract fun bindsArticlesFragment(): ArticlesFragment

    @ContributesAndroidInjector
    abstract fun bindsPlantDetailsFragment(): PlantDetailsFragment

    @ContributesAndroidInjector
    abstract fun bindsPlantsListFragment(): PlantsListFragment

    @ContributesAndroidInjector
    abstract fun bindsDiscoverFragment(): DiscoverFragment

    @ContributesAndroidInjector
    abstract fun bindsMyPlantsFragment(): MyPlantsFragment

    @ContributesAndroidInjector
    abstract fun bindsRemindersFragment(): RemindersFragment

    @ContributesAndroidInjector
    abstract fun bindsRemindersListFragment(): RemindersListFragment

    @ContributesAndroidInjector
    abstract fun bindsSettingsFragment(): SettingsFragment
}