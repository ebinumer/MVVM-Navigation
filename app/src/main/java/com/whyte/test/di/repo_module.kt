package com.whyte.test.di

import com.whyte.test.data.repo.repoImpl.RepoCategoryImpl
import com.whyte.test.data.repo.repoImpl.RepoListImpl
import com.whyte.test.data.repo.repoImpl.RepoLoginImpl
import org.koin.dsl.module

val repo = module {
    factory { RepoLoginImpl(get()) }
    factory { RepoCategoryImpl(get()) }
    factory { RepoListImpl(get()) }

}